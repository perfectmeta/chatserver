package com.perfectword.semantic_kernal.skill_define;

import com.perfectword.semantic_kernal.abstractions.Verify;
import com.perfectword.semantic_kernal.ai.AIException;
import com.perfectword.semantic_kernal.ai.text_completion.CompleteRequestSettings;
import com.perfectword.semantic_kernal.ai.text_completion.ITextCompletion;
import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.semantic_functions.SemanticFunctionConfig;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static com.perfectword.semantic_kernal.Util.nullOrWhitespace;

interface TriFunction<T1, T2, T3, R> {
    R function(T1 t1, T2 t2, T3 t3);
}

public class SKFunction {
    private String name;
    private String skillName;
    private String description;
    private boolean isSemantic;
    private CompleteRequestSettings aiRequestSettings;
    private List<ParameterView> parameters;

    public static ISKFunction fromSemanticConfig(String skillName, String functionName, SemanticFunctionConfig functionConfig) {
        Verify.notNull(functionConfig);

        TriFunction<ITextCompletion, CompleteRequestSettings, SKContext, Future<SKContext>> localFunc =
            (ITextCompletion client, CompleteRequestSettings requestSettings, SKContext context) -> {
                FutureTask<SKContext> task = new FutureTask<SKContext>(()->{
                    try {
                        String prompt = functionConfig.getPromptTemplate().renderAsync(context).get();
                        String completion = client.completeAsync(prompt, requestSettings).get();
                        context.getVariables().update(completion);
                    }
                    catch (AIException ex) {
                        context.fail(ex.message, ex);
                    }
                    return context;
                });

                Thread.startVirtualThread(task);
                return task;
            };
        return new SKFunction();
    }

    public enum DelegateTypes
    {
        Unknown = 0,
        Void = 1,
        OutString = 2,
        OutTaskString = 3,
        InSKContext = 4,
        InSKContextOutString = 5,
        InSKContextOutTaskString = 6,
        ContextSwitchInSKContextOutTaskSKContext = 7,
        InString = 8,
        InStringOutString = 9,
        InStringOutTaskString = 10,
        InStringAndContext = 11,
        InStringAndContextOutString = 12,
        InStringAndContextOutTaskString = 13,
        ContextSwitchInStringAndContextOutTaskContext = 14,
        InStringOutTask = 15,
        InContextOutTask = 16,
        InStringAndContextOutTask = 17,
        OutTask = 18
    }

    public String getName() {
        return name;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSemantic() {
        return isSemantic;
    }

    public CompleteRequestSettings getRequestSettings() {
        return aiRequestSettings;
    }

    public List<ParameterView> getParameters() {
        return parameters;
    }

    public static SKFunction fromNativeMethod(
            Method methodSignature,
            Object methodContainerInstance,
            String skillName
    ) {
        // todo implement this
        if (nullOrWhitespace(skillName)) {
            // skillName = SkillCollection.getGlobalSkill
        }
        return null;
    }
}
