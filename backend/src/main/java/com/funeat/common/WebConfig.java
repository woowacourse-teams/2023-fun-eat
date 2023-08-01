package com.funeat.common;

import com.funeat.auth.util.AuthArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CustomPageableHandlerMethodArgumentResolver customPageableHandlerMethodArgumentResolver;
    private final AuthArgumentResolver authArgumentResolver;

    public WebConfig(final CustomPageableHandlerMethodArgumentResolver customPageableHandlerMethodArgumentResolver,
                     final AuthArgumentResolver authArgumentResolver) {
        this.customPageableHandlerMethodArgumentResolver = customPageableHandlerMethodArgumentResolver;
        this.authArgumentResolver = authArgumentResolver;
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new StringToCategoryTypeConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customPageableHandlerMethodArgumentResolver);
        resolvers.add(authArgumentResolver);
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
