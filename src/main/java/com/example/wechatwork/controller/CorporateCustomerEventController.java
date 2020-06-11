package com.example.wechatwork.controller;

import com.example.wechatwork.config.SecurityConfig;
import com.example.wechatwork.config.WechatWorkConfig;
import com.example.wechatwork.model.AddCorporateCustomerEvent;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CorporateCustomerEventController {
    @Autowired
    private WechatWorkConfig wechatWorkConfig;

    @Autowired
    private SecurityConfig securityConfig;

    @GetMapping("/corporate-customer-event")
    public ResponseEntity<?> echo(@RequestParam("msg_signature") String message,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("echostr") String echostr) {
        System.out.println(message);
        System.out.println(echostr);

        WxCryptUtil wxCryptUtil = new WxCryptUtil(securityConfig.getToken(),
                securityConfig.getKey(),
                wechatWorkConfig.getCorpid());

        String decrypt = wxCryptUtil.decrypt(echostr);

        return new ResponseEntity<>(decrypt, HttpStatus.OK);
    }

    @PostMapping(path = "/corporate-customer-event", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> callback(@RequestParam("msg_signature") String message,
                                      @RequestParam("nonce") String nonce,
                                      @RequestBody AddCorporateCustomerEvent event){
        System.out.println(event);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}