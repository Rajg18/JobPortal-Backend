package com.JobPortal.JobPortal.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title       = "Job Portal API",
        version     = "1.0",
        description = "REST API for a college placement job portal. " +
                      "Supports user registration, JWT login, job listings, " +
                      "applications, and profile management."
    )
)
@SecurityScheme(
    name         = "bearerAuth",
    type         = SecuritySchemeType.HTTP,
    scheme       = "bearer",
    bearerFormat = "JWT"
)
public class OpenApiConfig {
    // springdoc picks up @OpenAPIDefinition and @SecurityScheme automatically.
    // No bean methods needed here.
}
