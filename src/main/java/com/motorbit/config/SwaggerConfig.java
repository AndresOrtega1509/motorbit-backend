package com.motorbit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                    .info(
                        new Info().title("Motorbit API")
                        .version("1.0.0")
                        .description("""
                                API REST para administrar clientes, vehículos 
                                y órdenes de servicio, facilitando el seguimiento 
                                de los trabajos realizados en un taller automotriz.
                                """)
                        .contact(new Contact()
                            .name("Andres Ortega")
                            .email("andresortega2273@gmail.com")
                        )
                    ).addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                    .components(new Components().addSecuritySchemes(securitySchemeName, 
                        new SecurityScheme()
                                    .name(securitySchemeName)
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")));
    }
}