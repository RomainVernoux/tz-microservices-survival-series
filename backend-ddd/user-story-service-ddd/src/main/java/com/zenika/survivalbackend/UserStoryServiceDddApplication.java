package com.zenika.survivalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class UserStoryServiceDddApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserStoryServiceDddApplication.class, args);
    }

}
