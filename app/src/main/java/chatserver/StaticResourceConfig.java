package chatserver;

import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    private static final Logger logger = Logger.getLogger(StaticResourceConfig.class.getName());
    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        String resourcePath = System.getenv("static_dir");
        String headIconPath = System.getenv("head_dir");
        if (Strings.isNullOrEmpty(resourcePath)) {
            try {
                Files.createFile(Path.of("./static"));
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
            resourcePath = "./static";
        }

        if (Strings.isNullOrEmpty(headIconPath)) {
            try {
                Files.createFile(Path.of("./head"));
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
            headIconPath = "./head";
        }
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:" + resourcePath + "/");
        logger.info("register static path " + resourcePath);
        registry.addResourceHandler("/headicon/**")
                .addResourceLocations("file:" + headIconPath + "/");
        logger.info("register head path " + headIconPath);
    }
}
