package com.funeat.common;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CustomPageableHandlerMethodArgumentResolver customPageableHandlerMethodArgumentResolver;

    public WebConfig(final CustomPageableHandlerMethodArgumentResolver customPageableHandlerMethodArgumentResolver) {
        this.customPageableHandlerMethodArgumentResolver = customPageableHandlerMethodArgumentResolver;
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new StringToCategoryTypeConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customPageableHandlerMethodArgumentResolver);
    }
}
