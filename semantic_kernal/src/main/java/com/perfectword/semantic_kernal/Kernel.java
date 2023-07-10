package com.perfectword.semantic_kernal;

import com.perfectword.semantic_kernal.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernal.orchestration.ContextVariables;
import com.perfectword.semantic_kernal.orchestration.SKContext;
import com.perfectword.semantic_kernal.semantic_functions.SemanticFunctionConfig;
import com.perfectword.semantic_kernal.skill_define.ISKFunction;
import com.perfectword.semantic_kernal.skill_define.SkillCollection;
import com.perfectword.semantic_kernal.template_engine.IPromptTemplateEngine;

import java.util.Map;
import java.util.concurrent.Future;

public class Kernel {
    private SkillCollection skills;
    private ISemanticTextMemory memory;
    private IPromptTemplateEngine promptTemplateEngine;



    public ISemanticTextMemory getMemory() {
        return memory;
    }

    
    public IPromptTemplateEngine getPromptTemplateEngine() {
        return promptTemplateEngine;
    }

    
    public SkillCollection getSkills() {
        return skills;
    }


    
    public ISKFunction registerSemanticFunction(String skillName, String functionName, SemanticFunctionConfig functionConfig) {
        Verify.validSkillName(skillName);
        Verify.ValidFunctionName(functionName);
        ISKFunction function = createSemanticFunction(skillName, functionName, functionConfig);
        return null;
    }

    
    public ISKFunction registerCustomFunction(String skillName, ISKFunction customFunction) {
        return null;
    }

    
    public Map<String, ISKFunction> importSkill(Object skillInstance, String skillName) {
        return null;
    }

    
    public void registerMemory(ISemanticTextMemory memory) {

    }

    
    public Future<SKContext> runAsync(ISKFunction... pipeline) {
        return null;
    }

    
    public Future<SKContext> runAsync(String input, ISKFunction... pipeline) {
        return null;
    }

    
    public Future<SKContext> runAsync(ContextVariables variables, ISKFunction... pipeline) {
        return null;
    }

    
    public ISKFunction func(String skillName, String functionName) {
        return null;
    }

    
    public SKContext createNewContext() {
        return null;
    }

    
    public <T> T getService(String name, Class<T> clazz) {
        return null;
    }


}
