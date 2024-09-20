package com.example.socialscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SocialMediaScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaScraperApplication.class, args);
    }

}
