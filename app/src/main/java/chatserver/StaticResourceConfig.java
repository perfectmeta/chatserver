package chatserver;

import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        String resourcePath = System.getenv("static_dir");
        if (Strings.isNullOrEmpty(resourcePath)) {
            try {
                Files.createFile(Path.of("./static"));
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
            resourcePath = "./static";
        }
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:" + resourcePath + "/");
    }
}
