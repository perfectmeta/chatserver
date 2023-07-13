package com.perfectword.semantic_kernel.skill_define;

import com.perfectword.semantic_kernel.Verify;
import com.perfectword.semantic_kernel.orchestration.SKContext;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


class MethodDetail {
    public boolean hasSKFunctionAnnotation;
    public MethodHandle handle;
    public DelegateTypes delegateTypes;
    public ParametersConverter parametersConverter;
    public List<ParameterView> parameters;
    public String name;
    public String description;
}


enum DelegateTypes
{
    Unknown,
    Void,
    OutString,
    OutTaskString,
    InSKContext,
    InSKContextOutString,
    InSKContextOutTaskString,
    ContextSwitchInSKContextOutTaskSKContext,
    InString,
    InStringOutString,
    InStringOutTaskString,
    InStringAndContext,
    InStringAndContextOutString,
    InStringAndContextOutTaskString,
    ContextSwitchInStringAndContextOutTaskContext,
    InStringOutTask,
    InContextOutTask,
    InStringAndContextOutTask,
    OutTask
}

public class NativeFunction implements ISKFunction {

    private FunctionView view;
    private Object delegateOwner;
    // private MethodHandle handle;
    private Method handle;

    public NativeFunction(
            Method methodSignature,
            Object methodContainerInstance,
            String skillName
    ) {
        delegateOwner = methodContainerInstance;
        var methodDetail = getMethodDetail(methodSignature, methodContainerInstance);
        view = new FunctionView(methodDetail.name, skillName, methodDetail.description, false, methodDetail.parameters);
        // handle = methodDetail.handle;
        handle = methodSignature;
    }


    @Override
    public FunctionView view() {
        return view;
    }

    @Override
    public SKContext invoke(SKContext context) {
        List<Object> parameters = new ArrayList<>();
        for (var pv : view.parameters()) {
            var p = context.getVariables().get(pv.name());
            if (p != null) {
                parameters.add(p);
            } else {
                throw new IllegalStateException("parameter %s is null".formatted(pv.name()));
            }
        }

        try {
            String result;
            if (delegateOwner != null) {
                result = (String)handle.invoke(delegateOwner, parameters.toArray());
            } else {
                result = (String)handle.invoke(parameters.toArray());
            }
            if (result != null) {
                context.getVariables().setInput(result);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return context;
    }

    private static MethodDetail getMethodDetail(Method methodSignature,
                                         Object methodContainerInstance) {
        Verify.NotNull(methodSignature);
        MethodDetail detail = new MethodDetail();
        detail.name = methodSignature.getName();
        detail.parameters = new ArrayList<>();

        SKFunction functionAnnotation = methodSignature.getDeclaredAnnotation(SKFunction.class);
        if (functionAnnotation != null) {
            detail.name = functionAnnotation.name();
            detail.hasSKFunctionAnnotation = true;
        } else {
            detail.name = methodSignature.getName();
        }

        detail.parameters = getParameterViews(methodContainerInstance, methodSignature);
        detail.parametersConverter = getParameterConverter(methodSignature);
        detail.delegateTypes = getDelegateTypes(methodSignature);
        detail.handle = getHandle(methodSignature, methodContainerInstance);
        return detail;
    }

    private static ParametersConverter getParameterConverter(Method methodSignature) {
        return ParametersConverter.of(methodSignature);
    }

    private static MethodHandle getHandle(Method methodSignature, Object methodContainerInstance) {
        var lookup = MethodHandles.lookup();
        try {
            return lookup.unreflect(methodSignature);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<ParameterView> getParameterViews(Object target, Method methodSignature) {
        List<ParameterView> parameters = new ArrayList<>();
        for (var p : methodSignature.getParameters()) {
            SKParameter annotation = p.getAnnotation(SKParameter.class);
            Type type = p.getParameterizedType();
            if (annotation != null) {
                parameters.add(new ParameterView(annotation.name(), annotation.description(), annotation.defaultValue()));
            } else {
                throw new RuntimeException("Function %s 's raw parameter %s not declared"
                        .formatted(methodSignature.getName(), p.getName()));
            }
        }
        return parameters;
    }

    private static DelegateTypes getDelegateTypes(Method method) {
        // todo implement this method
        return null;
    }

    private void constructView(String skillName, Method method, Object container) {
    }
}
