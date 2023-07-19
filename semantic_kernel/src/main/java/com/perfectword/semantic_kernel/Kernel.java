package com.perfectword.semantic_kernel;

import com.perfectword.semantic_kernel.ai.embeddings.IEmbeddingGeneration;
import com.perfectword.semantic_kernel.ai.text_completion.ITextCompletion;
import com.perfectword.semantic_kernel.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.semantic_functions.PromptTemplate;
import com.perfectword.semantic_kernel.semantic_functions.PromptTemplateConfig;
import com.perfectword.semantic_kernel.skill_define.*;
import com.perfectword.semantic_kernel.template_engine.IPromptTemplateEngine;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public record Kernel(SkillCollection skills,
                     ISemanticTextMemory memory,
                     ITextCompletion textCompletion,
                     IEmbeddingGeneration embeddingGeneration,
                     IPromptTemplateEngine promptTemplateEngine) {

    public void registerFunction(ISKFunction func) {
        skills.addFunction(func);
    }

    public SKContext runFunction(SKContext ctx, ISKFunction function) {
        return function.invoke(ctx);
    }


    public void importSkill(Object skillInstance, String skillName) {
        for (Method method : skillInstance.getClass().getDeclaredMethods()) {
            SKFunction anno = method.getAnnotation(SKFunction.class);
            if (anno != null) {
                Object methodContainerInstance = skillInstance;
                if (Modifier.isStatic(method.getModifiers())) {
                    methodContainerInstance = null;
                }
                ISKFunction func = new NativeFunction(method, methodContainerInstance, skillName);
                registerFunction(func);
            }
        }
    }

    public void importSkillFromDirectory(Path rootDir) throws IOException {
        String configFile = "config.json";
        String promptFile = "skprompt.txt";

        Verify.directoryExists(rootDir);
        List<Path> skillDirList = Files.list(rootDir).toList();

        for (Path skillDir : skillDirList) {
            String skillName = skillDir.getName(skillDir.getNameCount() - 1).toString();
            List<Path> functionDirList = Files.list(skillDir).toList();

            for (Path functionDir : functionDirList) {
                String functionName = functionDir.getName(functionDir.getNameCount() - 1).toString();

                var promptPath = functionDir.resolve(promptFile);
                if (!Files.exists(promptPath)) {
                    continue;
                }
                String promptStr = Files.readString(promptPath, StandardCharsets.UTF_8);

                var configPath = functionDir.resolve(configFile);
                if (!Files.exists(configPath)) {
                    continue;
                }
                String configStr = Files.readString(promptPath, StandardCharsets.UTF_8);
                PromptTemplateConfig config = KernelConfig.parseSkillConfigJson(configStr);

                var template = new PromptTemplate(promptStr, config, promptTemplateEngine);
                SemanticFunction func = new SemanticFunction(skillName, functionName, template, textCompletion);
                registerFunction(func);
            }
        }


    }

}
