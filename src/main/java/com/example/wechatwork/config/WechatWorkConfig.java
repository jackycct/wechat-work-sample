package com.example.wechatwork.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class WechatWorkConfig {
    @Value( "${wechatwork.corpid}" )
    private String corpid;

    @Value( "${wechatwork.corpsecret}" )
    private String corpsecret;

    public String getCorpid() {
        return corpid;
    }

    public String getCorpsecret() {
        return corpsecret;
    }
}