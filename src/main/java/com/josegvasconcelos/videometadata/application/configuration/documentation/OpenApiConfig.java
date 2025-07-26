package com.josegvasconcelos.videometadata.application.configuration.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI videoMetadataOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Video Metadata Service API")
                        .version("v1.0")
                        .description("Endpoints to import and query video metadata and statistics")
                        .contact(new Contact()
                                .name("José Vasconcelos")
                                .email("jgvasconcelos.dev@gmail.com")
                        )
                )
                .servers(Collections.singletonList(
                        new Server()
                                .url("http://localhost:7000")
                                .description("Local server")
                        )
                )
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                );
    }
}
