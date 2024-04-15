package com.adamdubaj.custompostgrestarter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("custom.starter")
@Validated
public record CustomProperties(
        @DefaultValue("jdbc:postgresql://localhost:5432/postgres") String url,
        @DefaultValue("postgres") String userName,
        @DefaultValue("admin") String password,
        @DefaultValue("org.postgresql.Driver") String driverClassName) {}
