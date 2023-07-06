package com.perfectword.semantic_kernal;

import com.perfectword.semantic_kernal.abstractions.Verify;
import com.perfectword.semantic_kernal.ai.AIException;
import com.perfectword.semantic_kernal.ai.embeddings.IEmbeddingGeneration;
import com.perfectword.semantic_kernal.ai.text_completion.CompleteRequestSettings;
import com.perfectword.semantic_kernal.ai.text_completion.ITextCompletion;
import com.perfectword.semantic_kernal.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernal.orchestration.ContextVariables;
import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.semantic_functions.SemanticFunctionConfig;
import com.perfectword.semantic_kernal.skill_define.*;
import com.perfectword.semantic_kernal.template_engine.IPromptTemplateEngine;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Function;

public class Kernel implements IKernel{
    // todo implement this class

    private KernelConfig config;
    private ISkillCollection skillCollection;
    private ISemanticTextMemory memory;
    private IPromptTemplateEngine promptTemplateEngine;

    @Override
    public KernelConfig getConfig() {
        return config;
    }

    @Override
    public ISemanticTextMemory getMemory() {
        return memory;
    }

    @Override
    public IPromptTemplateEngine getPromptTemplateEngine() {
        return promptTemplateEngine;
    }

    @Override
    public IReadOnlySkillCollection getSkills() {
        return skillCollection.getReadOnlySkillCollection();
    }

    @Override
    public ISKFunction registerSemanticFunction(String functionName, SemanticFunctionConfig functionConfig) {
        return registerSemanticFunction(SkillCollection.GLOBAL_SKILL, functionName, functionConfig);
    }

    @Override
    public ISKFunction registerSemanticFunction(String skillName, String functionName, SemanticFunctionConfig functionConfig) {
        Verify.validSkillName(skillName);
        Verify.ValidFunctionName(functionName);
        ISKFunction function = createSemanticFunction(skillName, functionName, functionConfig);
        return null;
    }

    @Override
    public ISKFunction registerCustomFunction(String skillName, ISKFunction customFunction) {
        return null;
    }

    @Override
    public Map<String, ISKFunction> importSkill(Object skillInstance, String skillName) {
        return null;
    }

    @Override
    public void registerMemory(ISemanticTextMemory memory) {

    }

    @Override
    public Future<SKContext> runAsync(ISKFunction... pipeline) {
        return null;
    }

    @Override
    public Future<SKContext> runAsync(String input, ISKFunction... pipeline) {
        return null;
    }

    @Override
    public Future<SKContext> runAsync(ContextVariables variables, ISKFunction... pipeline) {
        return null;
    }

    @Override
    public ISKFunction func(String skillName, String functionName) {
        return null;
    }

    @Override
    public SKContext createNewContext() {
        return null;
    }

    @Override
    public <T> T getService(String name, Class<T> clazz) {
        // todo implement this method
        // fixme 这个泛型类型判断可能不对，注意后面着重测试修改一下
        if (clazz == ITextCompletion.class) {
            name = name != null ? name : config.DefaultServiceId;
            Function<IKernel, ITextCompletion> factory = config.getTextCompletionServices().get(name);
            if (factory == null) {
                throw new KernelException(KernelException.ErrorCodes.ServiceNotFound,
                        "'%s' text completion service not available".formatted(name));
            }
            var service = factory.apply(this);
            return clazz.cast(service);
        }

        if (clazz == IEmbeddingGeneration.class) {

        }
        return null;
    }

    public <T> T getService(Class<T> clazz) {
        return getService(null, clazz);
    }

    private ISKFunction createSemanticFunction(String skillName, String functionName, SemanticFunctionConfig functionConfig) {
        // todo implement
        if (functionConfig.getPromptTemplateConfig().getType().equalsIgnoreCase("completion")) {
            throw new AIException(AIException.ErrorCodes.FunctionTypeNotSupported,
                    "Function type not supported: %s".formatted(functionConfig.getPromptTemplateConfig()));
        }
        ISKFunction func = SKFunction.fromSemanticConfig(skillName, functionName, functionConfig);
        func.setDefaultSkillCollection(this.skillCollection);
        func.setAIConfiguration(CompleteRequestSettings.fromCompletionConfig(functionConfig.getPromptTemplateConfig().getCompletion()));
        func.setAIService(()->this.getService(ITextCompletion.class));
        return func;
    }
}
