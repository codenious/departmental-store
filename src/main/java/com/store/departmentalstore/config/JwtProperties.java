package com.store.departmentalstore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "store.security.jwt")
public record JwtProperties(String issuer, String secret) {
}
