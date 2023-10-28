package com.sv.oauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OauthServiceApplication  {


    public static void main(String[] args) {
        SpringApplication.run(OauthServiceApplication.class, args);
    }


}
