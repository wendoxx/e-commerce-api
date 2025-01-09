package org.example.oauth2resourceserverproject.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition (
        info = @Info (
                title = "E-commerce-API",
                version = "1.0",
                description = "API documentation for a E-commerce."
                )
)
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("E-commerce-API")
                        .version("1.0")
                        .description("API documentation for a E-commerce.")
                );
    }
}