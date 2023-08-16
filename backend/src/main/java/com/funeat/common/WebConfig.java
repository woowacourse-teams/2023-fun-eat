package com.funeat.common;

import com.funeat.auth.util.AuthArgumentResolver;
import com.funeat.auth.util.AuthHandlerInterceptor;
import com.funeat.recipe.util.RecipeHandlerInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CustomPageableHandlerMethodArgumentResolver customPageableHandlerMethodArgumentResolver;
    private final AuthArgumentResolver authArgumentResolver;
    private final AuthHandlerInterceptor authHandlerInterceptor;
    private final RecipeHandlerInterceptor recipeHandlerInterceptor;

    public WebConfig(final CustomPageableHandlerMethodArgumentResolver customPageableHandlerMethodArgumentResolver,
                     final AuthArgumentResolver authArgumentResolver,
                     final AuthHandlerInterceptor authHandlerInterceptor,
                     final RecipeHandlerInterceptor recipeHandlerInterceptor) {
        this.customPageableHandlerMethodArgumentResolver = customPageableHandlerMethodArgumentResolver;
        this.authArgumentResolver = authArgumentResolver;
        this.authHandlerInterceptor = authHandlerInterceptor;
        this.recipeHandlerInterceptor = recipeHandlerInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authHandlerInterceptor)
                .addPathPatterns("/api/products/**/reviews/**")
                .addPathPatterns("/api/members/**")
                .addPathPatterns("/api/logout");
        registry.addInterceptor(recipeHandlerInterceptor)
                .addPathPatterns("/api/recipes");
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
}
