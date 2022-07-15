package com.gbx.cardinalevaluator.application;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenAPI(@Value("v1") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Cardinal Evaluator API").version(appVersion)
                        .license(new License().name("GlobantX 1.0").url("http://springdoc.org")));
    }
}
