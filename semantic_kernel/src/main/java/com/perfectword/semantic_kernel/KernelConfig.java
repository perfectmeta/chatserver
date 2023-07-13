package com.perfectword.semantic_kernel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.perfectword.semantic_kernel.ai.embeddings.EmbeddingGeneration;
import com.perfectword.semantic_kernel.ai.text_completion.OpenAITextCompletion;
import com.perfectword.semantic_kernel.memory.NullMemory;
import com.perfectword.semantic_kernel.semantic_functions.PromptTemplateConfig;
import com.perfectword.semantic_kernel.skill_define.SkillCollection;
import com.perfectword.semantic_kernel.template_engine.PromptTemplateEngine;

import java.io.IOException;
import java.nio.file.Path;

public class KernelConfig {
    public KernelConfig(Path configFile) {
    }

    public Kernel create() {
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

    public static PromptTemplateConfig parseSkillConfigJson(String content) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper.readValue(content, PromptTemplateConfig.class);
    }

}
