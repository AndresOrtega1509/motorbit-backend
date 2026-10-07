package com.motorbit.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@ConfigurationProperties(prefix = "motorbit.jwt")
public record JwtProperties(
    @NotBlank 
    String secret,

    @NotNull 
    @Positive 
    long expiration
) {
}
