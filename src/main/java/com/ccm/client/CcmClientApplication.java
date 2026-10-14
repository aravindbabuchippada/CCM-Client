package com.ccm.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.ccm.client", "com.ccm.common"})
@ComponentScan(basePackages = { "com.ccm.*"})
@EntityScan(basePackages = {"com.ccm.client.entity", "com.ccm.common.entity"})
public class CcmClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcmClientApplication.class, args);
	}

}
