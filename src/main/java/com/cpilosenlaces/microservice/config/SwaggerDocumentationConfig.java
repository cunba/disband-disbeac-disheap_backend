package com.cpilosenlaces.microservice.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerDocumentationConfig {

    private final String TITLE = "Disheap Service";

    private final String DESCRIPTION = "Disheap API connection to Disbands and Disbeacs information";

    private final String BASE_PACKAGE = "com.cpilosenlaces.microservice.controller";

    @Bean
    public GroupedOpenApi disheapOpenApi() {
        return GroupedOpenApi.builder()
                .group("DisheapApi")
                .packagesToScan(BASE_PACKAGE + ".disheap")
                .build();
    }

    @Bean
    public GroupedOpenApi disbandOpenApi() {
        return GroupedOpenApi.builder()
                .group("DisbandApi")
                .packagesToScan(BASE_PACKAGE + ".disband")
                .build();
    }

    @Bean
    public GroupedOpenApi disbeacOpenApi() {
        return GroupedOpenApi.builder()
                .group("DisbeacApi")
                .packagesToScan(BASE_PACKAGE + ".disbeac")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(TITLE)
                        .description(DESCRIPTION)
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Irene Cunto").email("ire.cunba@gmail.com")));
    }
}
