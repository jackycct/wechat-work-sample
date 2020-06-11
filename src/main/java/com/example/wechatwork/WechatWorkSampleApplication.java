package com.example.wechatwork;

import com.example.wechatwork.gateway.WechatWorkGateway;
import com.example.wechatwork.model.GetTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WechatWorkSampleApplication implements CommandLineRunner {
	@Autowired
	private WechatWorkGateway gw;


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WechatWorkSampleApplication.class);
		app.run();
		// SpringApplication.run(WechatWorkSampleApplication.class, args);
	}

	public void run(String... args) throws Exception {
		GetTokenResponse res = gw.getAccessToken();
		System.out.println(res.getAccess_token());
	}
}
