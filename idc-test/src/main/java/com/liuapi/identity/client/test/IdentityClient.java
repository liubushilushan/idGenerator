package com.liuapi.identity.client.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

@SpringBootApplication
@ImportResource("classpath:consumer.xml")
public class IdentityClient {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(IdentityClient.class);
    }
}
