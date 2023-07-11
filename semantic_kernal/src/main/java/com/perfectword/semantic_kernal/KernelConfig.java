package com.perfectword.semantic_kernal;

import com.perfectword.semantic_kernal.ai.embeddings.EmbeddingGeneration;
import com.perfectword.semantic_kernal.ai.text_completion.TextCompletion;
import com.perfectword.semantic_kernal.memory.NullMemory;
import com.perfectword.semantic_kernal.skill_define.SkillCollection;
import com.perfectword.semantic_kernal.template_engine.PromptTemplateEngine;

import java.nio.file.Path;

public class KernelConfig {
    public KernelConfig(Path configFile) {
    }

    public Kernel create(){
        var skills = new SkillCollection();
        var promptTemplateEngine = new PromptTemplateEngine();
        var memory = new NullMemory();
        var completeService = new TextCompletion();
        var embeddingGeneration = new EmbeddingGeneration();
        return new Kernel(skills, memory, completeService, embeddingGeneration, promptTemplateEngine);
    }

    public static void importSkill(Kernel kernel, Path skillDir) {

    }
}
