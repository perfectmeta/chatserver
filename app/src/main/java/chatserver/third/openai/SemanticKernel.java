package chatserver.third.openai;

import com.perfectword.semantic_kernel.Kernel;
import com.perfectword.semantic_kernel.ai.embeddings.OpenAIEmbeddingGeneration;
import com.perfectword.semantic_kernel.ai.text_completion.OpenAITextCompletion;
import com.perfectword.semantic_kernel.memory.NullMemory;
import com.perfectword.semantic_kernel.skill_define.*;
import com.perfectword.semantic_kernel.template_engine.PromptTemplateEngine;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SemanticKernel {
    Map<String, Kernel> kernels = new HashMap<>();
    public static final SemanticKernel INSTANCE = new SemanticKernel();

    public Kernel getKernel(String robotId) {
        if (kernels.containsKey(robotId)) {
            return kernels.get(robotId);
        }
        constructKernel(robotId);
        return kernels.get(robotId);
    }

    private void constructKernel(String robotId) {
        var baseService = OpenAi.makeOpenAiService();
        Kernel kernel = new Kernel(new SkillCollection(),
                NullMemory.getInstance(),
                new OpenAITextCompletion(baseService, "gpt-3.5-turbo"),
                new OpenAIEmbeddingGeneration(baseService),
                new PromptTemplateEngine());
        Method weatherMethod;
        Method dateMethod;
        try {
            weatherMethod = this.getClass().getDeclaredMethod("getWeather", String.class);
            dateMethod = this.getClass().getDeclaredMethod("getDate");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        ISKFunction weatherFunction = new NativeFunction(weatherMethod, this, "inner");
        ISKFunction dateFunction = new NativeFunction(dateMethod, this, "inner");
        kernel.registerFunction(weatherFunction);
        kernel.registerFunction(dateFunction);
        kernels.put(robotId, kernel);
    }

    @SKFunction(name="getWeather", description="get the weather info by given location")
    public String getWeather(@SKParameter(name = "location", description = "the location")
                                 String location) {
        return location + "sunny now";
    }

    @SKFunction(name="getDate", description="get the date info")
    public String getDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(dateTimeFormatter);
    }
}
