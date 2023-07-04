package com.perfectword.semantic_kernal;

import com.perfectword.semantic_kernal.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernal.orchestration.ContextVariables;
import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.semantic_functions.SemanticFunctionConfig;
import com.perfectword.semantic_kernal.skill_define.IReadOnlySkillCollection;
import com.perfectword.semantic_kernal.skill_define.ISKFunction;
import com.perfectword.semantic_kernal.template_engine.IPromptTemplateEngine;

import java.util.Map;
import java.util.concurrent.Future;

public interface IKernel {
    KernelConfig getConfig();
    ISemanticTextMemory getMemory();
    IPromptTemplateEngine getPromptTemplateEngine();
    IReadOnlySkillCollection getSkills();
    ISKFunction registerSemanticFunction(String functionName, SemanticFunctionConfig functionConfig);
    ISKFunction registerSemanticFunction(String skillName, String functionName, SemanticFunctionConfig functionConfig);
    ISKFunction registerCustomFunction(String skillName, ISKFunction customFunction);
    Map<String, ISKFunction> importSkill(Object skillInstance, String skillName);
    default Map<String, ISKFunction> importSkill(Object skillInstance) {
        return importSkill(skillInstance, "");
    }
    void registerMemory(ISemanticTextMemory memory);
    Future<SKContext> runAsync(ISKFunction... pipeline);
    Future<SKContext> runAsync(String input, ISKFunction... pipeline);
    Future<SKContext> runAsync(ContextVariables variables, ISKFunction... pipeline);
    ISKFunction func(String skillName, String functionName);
    SKContext createNewContext();
    <T> T getService(String name, Class<T> clazz);
}
