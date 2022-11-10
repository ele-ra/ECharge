package com.echarge.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Getter
    @Value("${info.app.version}")
    private String versionApp;

    @Getter
    @Value("${info.db.version}")
    private String versionDb;
}