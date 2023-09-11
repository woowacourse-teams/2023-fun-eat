package com.funeat.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "펀잇 API 명세서",
                description = "펀잇팀 API 명세서입니다.",
                version = "v1"
        ),
        tags = {
                @Tag(name = "01.Product", description = "상품 기능"),
                @Tag(name = "02.Category", description = "카테고리 기능"),
                @Tag(name = "03.Review", description = "리뷰 기능"),
                @Tag(name = "04.Tag", description = "태그 기능"),
                @Tag(name = "05.Member", description = "사용자 기능"),
                @Tag(name = "06.Login", description = "로그인 기능"),
                @Tag(name = "07.Recipe", description = "꿀조합 기능"),
                @Tag(name = "08.S3", description = "S3 기능"),
        }
)
@Configuration
public class OpenApiConfig {
}
