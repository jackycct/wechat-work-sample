package com.example.wechatwork.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class SecurityConfig {
    @Value( "${security.token}" )
    private String token;

    @Value( "${security.key}" )
    private String key;

    public String getToken() {
        return token;
    }

    public String getKey() {
        return key;
    }
}