package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.KernelException;
import com.perfectword.semantic_kernel.KernelException.ErrorCodes;
import com.perfectword.semantic_kernel.Verify;
import com.perfectword.semantic_kernel.orchestration.SKContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;


public class NativeFunction implements ISKFunction {
    enum FunctionOutputType { // 就先考虑最简单情况，后面有需要再扩展
        OutputString,
        OutputVoid
    }

    private final FunctionView view;
    private final Object methodContainerInstance;
    private final Method handle;
    private final FunctionOutputType outputType;
    private boolean isFirstInputSKContext;

    public NativeFunction(
            Method methodSignature,
            Object methodContainerInstance,
            String skillName
    ) {
        Verify.notNull(methodSignature);

        String methodName = methodSignature.getName();
        this.methodContainerInstance = methodContainerInstance;
        SKFunction annotation = methodSignature.getDeclaredAnnotation(SKFunction.class);
        if (annotation == null) {
            throw new KernelException(ErrorCodes.InvalidFunctionDescription,
                    "%s has no annotation".formatted(methodName));
        }

        String functionName = annotation.name();
        if (functionName.isBlank()) {
            functionName = methodName;
        }
        String description = annotation.description();

        List<ParameterView> parameters = new ArrayList<>();
        Parameter[] params = methodSignature.getParameters();
        int idx = 0;
        for (Parameter p : params) {
            String pn = "input";
            String pd = "input";
            Class<?> pt = p.getType();
            // 支持第一个参数是context，其他参数是字符串；或全是字符串
            if (idx == 0 && SKContext.class.isAssignableFrom(pt)) {
                isFirstInputSKContext = true;
            } else if (!String.class.isAssignableFrom(pt)) {
                throw new KernelException(ErrorCodes.InvalidFunctionDescription,
                        "%s parameter type = %s not supported".formatted(methodName, pt));
            }

            SKParameter anno = p.getAnnotation(SKParameter.class);
            if (anno == null) {
                if (params.length != 1) {
                    throw new KernelException(ErrorCodes.InvalidFunctionDescription,
                            "%s parameter annotation not set".formatted(methodName));
                }else if (isFirstInputSKContext){
                    pn = pd =  "__SKContext__";
                }
            } else {
                pn = anno.name();
                pd = anno.description();
            }
            parameters.add(new ParameterView(pn, pd));

            idx++;
        }

        Class<?> returnType = methodSignature.getReturnType();
        if (String.class.isAssignableFrom(returnType)) {
            outputType = FunctionOutputType.OutputString;
        } else if (void.class.isAssignableFrom(returnType)) {
            outputType = FunctionOutputType.OutputVoid;
        } else {
            throw new KernelException(ErrorCodes.InvalidFunctionDescription,
                    "%s return type = %s not supported".formatted(methodName, returnType));
        }

        view = new FunctionView(functionName, skillName, description, false, parameters);
        handle = methodSignature;
    }


    @Override
    public FunctionView view() {
        return view;
    }

    @Override
    public SKContext invoke(SKContext context) {
        Object[] args = new Object[view.parameters().size()];
        int idx = 0;
        for (var pv : view.parameters()) {
            if (idx == 0 && isFirstInputSKContext) {
                args[0] = context;
            } else {
                var p = context.getVariables().get(pv.name());
                if (p != null) {
                    args[idx] = p;
                } else {
                    throw new KernelException(ErrorCodes.FunctionInvokeError,
                            "%s parameter %s not set".formatted(view.name(), pv.name()));
                }
            }

            idx++;

        }


        Object result;
        try {
            if (methodContainerInstance != null) {
                result = handle.invoke(methodContainerInstance, args);
            } else {
                result = handle.invoke(null, args);
            }
        } catch (Throwable e) {
            throw new KernelException(ErrorCodes.FunctionInvokeError, view.name(), e);
        }

        if (outputType == FunctionOutputType.OutputString) {
            context.getVariables().setInput((String) result);
        } // 如果不是返回string，这里input没有冲掉

        return context;
    }


}
