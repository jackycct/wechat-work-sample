package com.example.wechatwork.controller;

import com.example.wechatwork.config.SecurityConfig;
import com.example.wechatwork.model.AddCorporateCustomerEvent;
import com.example.wechatwork.security.AESKeyGenerator;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CorporateCustomerEventController {
    @Autowired
    private SecurityConfig securityConfig;

    @GetMapping("/corporate-customer-event")
    public String echo(@RequestParam("msg_signature") String message,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("echostr") String echostr) {
        System.out.println(message);
        System.out.println(echostr);

        AESKeyGenerator aesKeyGenerator = new AESKeyGenerator(securityConfig.getAlgorithm(), securityConfig.getKey());
        String decrypt = null;
        try {
            decrypt = aesKeyGenerator.decrypt(echostr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypt;
    }

    @PostMapping(path = "/corporate-customer-event", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> callback(@RequestParam("msg_signature") String message,
                                      @RequestParam("nonce") String nonce,
                                      @RequestBody AddCorporateCustomerEvent event){
        System.out.println(event);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}