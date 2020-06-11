package com.example.wechat.gateway;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

public class WechatWorkGateway {
    public String getAccessToken() {
        WebClient client = WebClient
                .builder()
                .baseUrl("https://qyapi.weixin.qq.com/")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        WebClient.ResponseSpec response;
        response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cgi-bin/gettoken")
                        .queryParam("corpid", "need-to-be-replaced")
                        .queryParam("corpsecret", "need-to-be-replaced")
                        .build())
                .retrieve();

        return response.bodyToMono(String.class).block();
    }

    public String getCallbackIp(String accessToken) {
        WebClient client = WebClient
                .builder()
                .baseUrl("https://qyapi.weixin.qq.com/")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        WebClient.ResponseSpec response;
        response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cgi-bin/getcallbackip")
                        .queryParam("access_token", accessToken)
                        .build())
                .retrieve();

        return response.bodyToMono(String.class).block();
    }
}
