package com.funeat.common;

import com.funeat.admin.util.AdminCheckInterceptor;
import com.funeat.auth.util.AuthArgumentResolver;
import com.funeat.auth.util.AuthHandlerInterceptor;
import com.funeat.recipe.util.RecipeDetailHandlerInterceptor;
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
    private final RecipeDetailHandlerInterceptor recipeDetailHandlerInterceptor;

    private final AdminCheckInterceptor adminCheckInterceptor;

    public WebConfig(final CustomPageableHandlerMethodArgumentResolver customPageableHandlerMethodArgumentResolver,
                     final AuthArgumentResolver authArgumentResolver,
                     final AuthHandlerInterceptor authHandlerInterceptor,
                     final RecipeHandlerInterceptor recipeHandlerInterceptor,
                     final RecipeDetailHandlerInterceptor recipeDetailHandlerInterceptor,
                     final AdminCheckInterceptor adminCheckInterceptor) {
        this.customPageableHandlerMethodArgumentResolver = customPageableHandlerMethodArgumentResolver;
        this.authArgumentResolver = authArgumentResolver;
        this.authHandlerInterceptor = authHandlerInterceptor;
        this.recipeHandlerInterceptor = recipeHandlerInterceptor;
        this.recipeDetailHandlerInterceptor = recipeDetailHandlerInterceptor;
        this.adminCheckInterceptor = adminCheckInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authHandlerInterceptor)
                .addPathPatterns("/api/products/**/reviews/**")
                .addPathPatterns("/api/members/**")
                .addPathPatterns("/api/logout");
        registry.addInterceptor(recipeHandlerInterceptor)
                .addPathPatterns("/api/recipes");
        registry.addInterceptor(recipeDetailHandlerInterceptor)
                .addPathPatterns("/api/recipes/**")
                .excludePathPatterns("/api/recipes");
        registry.addInterceptor(adminCheckInterceptor)
                .excludePathPatterns("/api/admin/login")
                .addPathPatterns("/api/admin/**");
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new StringToCategoryTypeConverter());
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customPageableHandlerMethodArgumentResolver);
        resolvers.add(authArgumentResolver);
    }
}
