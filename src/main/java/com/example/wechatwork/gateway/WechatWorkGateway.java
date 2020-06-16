package com.example.wechatwork.gateway;

import com.example.wechatwork.config.WechatWorkConfig;
import com.example.wechatwork.model.GetTokenResponse;
import lombok.val;
import me.chanjar.weixin.common.api.WxConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;

@Component
public class WechatWorkGateway {
    @Autowired
    private WechatWorkConfig config;

    public GetTokenResponse getAccessToken() {
        WebClient client = WebClient
                .builder()
                .baseUrl("https://qyapi.weixin.qq.com/")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        WebClient.ResponseSpec response;
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cgi-bin/gettoken")
                        .queryParam("corpid", config.getCorpid())
                        .queryParam("corpsecret", config.getCorpsecret())
                        .build())
                .retrieve()
                .bodyToMono(GetTokenResponse.class)
                .block();
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

    public String send(String accessToken, String touser, String agentid, String message) {
        WebClient client = WebClient
                .builder()
                .baseUrl("https://qyapi.weixin.qq.com/")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        val body = new HashMap<String, Object>();
        body.put("ToUser", touser);
        body.put("MsgType", "text");
        body.put("AgentID", agentid);
        val content = new HashMap<String, String>();
        content.put("Content", message);
        body.put("text", content);

        WebClient.ResponseSpec response;
        response = client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/cgi-bin/message/send")
                        .queryParam("access_token", accessToken)
                        .build(body))
                .retrieve();

        return response.bodyToMono(String.class).block();
    }
}
