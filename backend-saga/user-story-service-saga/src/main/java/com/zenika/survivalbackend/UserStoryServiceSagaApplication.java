package com.zenika.survivalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@EnableAspectJAutoProxy
public class UserStoryServiceSagaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserStoryServiceSagaApplication.class, args);
    }

}
