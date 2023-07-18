package com.perfectword.semantic_kernel;

import com.perfectword.semantic_kernel.ai.embeddings.IEmbeddingGeneration;
import com.perfectword.semantic_kernel.ai.text_completion.ITextCompletion;
import com.perfectword.semantic_kernel.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernel.orchestration.ContextVariables;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.skill_define.ISKFunction;
import com.perfectword.semantic_kernel.skill_define.SkillCollection;
import com.perfectword.semantic_kernel.template_engine.IPromptTemplateEngine;

public class Kernel {
    private final SkillCollection skills;
    private final ISemanticTextMemory memory;
    private final ITextCompletion textCompletion;
    private final IEmbeddingGeneration embeddingGeneration;
    private final IPromptTemplateEngine promptTemplateEngine;

    public Kernel(SkillCollection skills,
                  ISemanticTextMemory memory,
                  ITextCompletion textCompletion,
                  IEmbeddingGeneration embeddingGeneration,
                  IPromptTemplateEngine promptTemplateEngine) {
        this.skills = skills;
        this.memory = memory;
        this.textCompletion = textCompletion;
        this.embeddingGeneration = embeddingGeneration;
        this.promptTemplateEngine = promptTemplateEngine;
    }


    public SkillCollection getSkills() {
        return skills;
    }

    public ISemanticTextMemory getMemory() {
        return memory;
    }

    public ITextCompletion getTextCompletion() {
        return textCompletion;
    }

    public IEmbeddingGeneration getEmbeddingGeneration() {
        return embeddingGeneration;
    }

    public IPromptTemplateEngine getPromptTemplateEngine() {
        return promptTemplateEngine;
    }

    public void registerFunction(ISKFunction func) {
        skills.addFunction(func);
    }

    public SKContext runFunction(ContextVariables variables, ISKFunction... pipeline) {
        return null;
    }

}
