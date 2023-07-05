package com.perfectword.semantic_kernal;

import com.perfectword.semantic_kernal.abstractions.Verify;
import com.perfectword.semantic_kernal.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernal.orchestration.ContextVariables;
import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.semantic_functions.SemanticFunctionConfig;
import com.perfectword.semantic_kernal.skill_define.IReadOnlySkillCollection;
import com.perfectword.semantic_kernal.skill_define.ISKFunction;
import com.perfectword.semantic_kernal.skill_define.ISkillCollection;
import com.perfectword.semantic_kernal.skill_define.SkillCollection;
import com.perfectword.semantic_kernal.template_engine.IPromptTemplateEngine;

import java.util.Map;
import java.util.concurrent.Future;

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
        return null;
    }

//    private ISKFunction createSemanticFunction(String skillName, String functionName, SemanticFunctionConfig functionConfig) {
//        // todo implement
//        if (functionConfig.getPromptTemplateConfig().getType().equalsIgnoreCase("completion")) {
//
//        }
//    }
}
