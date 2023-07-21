package com.perfectword.semantic_kernel;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.perfectword.semantic_kernel.ai.embeddings.IEmbeddingGeneration;
import com.perfectword.semantic_kernel.ai.text_completion.ITextCompletion;
import com.perfectword.semantic_kernel.memory.ISemanticTextMemory;
import com.perfectword.semantic_kernel.orchestration.SKContext;
import com.perfectword.semantic_kernel.semantic_functions.PromptTemplate;
import com.perfectword.semantic_kernel.semantic_functions.PromptTemplateConfig;
import com.perfectword.semantic_kernel.skill_define.*;
import com.perfectword.semantic_kernel.template_engine.IPromptTemplateEngine;
import com.theokanning.openai.completion.chat.ChatFunction;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Kernel {
    public final SkillCollection skills;
    public final ISemanticTextMemory memory;
    public final ITextCompletion textCompletion;
    public final IEmbeddingGeneration embeddingGeneration;
    public final IPromptTemplateEngine promptTemplateEngine;

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

    private final List<ChatFunction> chatFunctions = new ArrayList<>();
    private boolean chatFunctionsDirty = true;

    public void registerFunction(ISKFunction func) {
        skills.addFunction(func);
        chatFunctionsDirty = true;
    }

    public List<ChatFunction> getChatFunctions() {
        if (!chatFunctionsDirty) {
            return chatFunctions;
        }

        constructChatFunctions();
        chatFunctionsDirty = false;
        return chatFunctions;
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
        chatFunctionsDirty = true;
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
                String configStr = Files.readString(configPath, StandardCharsets.UTF_8);
                PromptTemplateConfig config = KernelConfig.parseSkillConfigJson(configStr);

                var template = new PromptTemplate(promptStr, config, promptTemplateEngine);
                SemanticFunction func = new SemanticFunction(skillName, functionName, template, textCompletion);
                registerFunction(func);
            }
        }
        chatFunctionsDirty = true;
    }

    private void constructChatFunctions() {
        chatFunctions.clear();
        List<ChatFunction> tmpChatFunctions = new ArrayList<>();
        for (var skillName : skills.getSkills()) {
            var functions = skills.getSkillFunctions(skillName);
            for (var function : functions) {
                if (function != null) {
                    tmpChatFunctions.add(constructChatFunction(function));
                }
            }
        }
        chatFunctions.addAll(tmpChatFunctions);
    }

    private static ChatFunction constructChatFunction(ISKFunction function) {
        return ChatFunction.builder()
                .name(function.view().name())
                .description(function.view().description())
                .executor(Property.class, x -> function.invoke(null))
                .build();
    }
}

class Property {
    @JsonPropertyDescription("City and state, for example: León, Guanajuato")
    public String location;
}
