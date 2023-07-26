package chatserver;

import chatserver.security.Secrets;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    private static final Logger logger = Logger.getLogger(StaticResourceConfig.class.getName());

    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        String resourcePath = Secrets.DATA_STATIC_DIR;
        String headIconPath = Secrets.DATA_HEAD_ICON_DIR;

        try {
            Files.createDirectories(Paths.get(resourcePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Files.createDirectories(Paths.get(headIconPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:" + resourcePath + "/");
        logger.info("register static path " + resourcePath);
        registry.addResourceHandler("/headicon/**")
                .addResourceLocations("file:" + headIconPath + "/");
        logger.info("register head path " + headIconPath);
    }
}
