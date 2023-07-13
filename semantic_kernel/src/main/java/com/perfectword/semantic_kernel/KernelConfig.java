package com.perfectword.semantic_kernel;

import com.perfectword.semantic_kernel.ai.embeddings.EmbeddingGeneration;
import com.perfectword.semantic_kernel.ai.text_completion.OpenAITextCompletion;
import com.perfectword.semantic_kernel.memory.NullMemory;
import com.perfectword.semantic_kernel.skill_define.SkillCollection;
import com.perfectword.semantic_kernel.template_engine.PromptTemplateEngine;

import java.nio.file.Path;

public class KernelConfig {
    public KernelConfig(Path configFile) {
    }

    public Kernel create(){
        var skills = new SkillCollection();
        var promptTemplateEngine = new PromptTemplateEngine();
        var memory = new NullMemory();
        // todo fix this
        var completeService = new OpenAITextCompletion(null, "");
        var embeddingGeneration = new EmbeddingGeneration();
        return new Kernel(skills, memory, completeService, embeddingGeneration, promptTemplateEngine);
    }

    public static void importSkill(Kernel kernel, Path skillDir) {

    }
}
