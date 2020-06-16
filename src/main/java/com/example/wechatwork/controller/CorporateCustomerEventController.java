package com.example.wechatwork.controller;

import com.example.wechatwork.config.WechatWorkConfig;
import com.example.wechatwork.gateway.WechatWorkGateway;
import com.example.wechatwork.model.GetTokenResponse;
import lombok.val;
import me.chanjar.weixin.common.util.XmlUtils;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class CorporateCustomerEventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorporateCustomerEventController.class);

    @Autowired
    private WechatWorkConfig wechatWorkConfig;
    @Autowired
    private WechatWorkGateway gw;

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    @GetMapping("/corporate-customer-event")
    public ResponseEntity<?> echo(@RequestParam("msg_signature") String msg_signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("echostr") String echostr) {
        WxCryptUtil wxCryptUtil = new WxCryptUtil(wechatWorkConfig.getExternalContactToken(),
                wechatWorkConfig.getExternalContactAesKey(),
                wechatWorkConfig.getCorpid());

        String decrypt = wxCryptUtil.decrypt(echostr);

        return new ResponseEntity<>(decrypt, HttpStatus.OK);
    }

    @PostMapping(path = "/corporate-customer-event")
    public ResponseEntity<?> callback(@RequestParam("msg_signature") String message,
                                      @RequestParam("nonce") String nonce,
                                      @RequestBody String eventString){

        WxCryptUtil wxCryptUtil = new WxCryptUtil(wechatWorkConfig.getExternalContactToken(),
                wechatWorkConfig.getExternalContactAesKey(),
                wechatWorkConfig.getCorpid());

        // Uncomment this line to write some payload for testing  later on
        // writeStringToFile(eventString);

        val msgEnvelope = XmlUtils.xml2Map(eventString);

        String encryptedXml = String.valueOf(msgEnvelope.get("Encrypt"));
        String decryptedXml = wxCryptUtil.decrypt(encryptedXml);

        val msgContent = XmlUtils.xml2Map(decryptedXml);

        System.out.println("********** New Message received **************");
        msgContent.forEach((k, v) -> System.out.println(k + " : " + v));

        if ("enter_agent".equals(msgContent.get("Event"))) {
            GetTokenResponse res = gw.getAccessToken();
            LOGGER.debug("Access Token: " + res.getAccess_token());
            String results = gw.send(res.getAccess_token(), (String) msgContent.get("FromUserName"), (String) msgContent.get("AgentID"), (String) msgContent.get("welcome!!!"));
            System.out.println(results);

            // TODO Fix this error
            // {"errcode":40008,"errmsg":"invalid message type, hint: [1592319353_77_4e699f9e415228292dde839554c6191c], from ip: 220.246.156.20, more info at https://open.work.weixin.qq.com/devtool/query?e=40008"}
        }
        return new ResponseEntity<>("reply", HttpStatus.OK);
    }

    private void writeStringToFile(String eventString) {
        try (var fr = new FileWriter("eventString" + System.currentTimeMillis(), StandardCharsets.UTF_8)) {
            fr.write(eventString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}