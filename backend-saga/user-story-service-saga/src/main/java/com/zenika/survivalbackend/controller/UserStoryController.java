package com.zenika.survivalbackend.controller;

import com.zenika.survivalbackend.model.UserStory;
import com.zenika.survivalbackend.model.UserStoryEvent;
import com.zenika.survivalbackend.service.UserStoryService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-stories")
public class UserStoryController {

    private UserStoryService userStoryService;

    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    public UserStoryController(UserStoryService userStoryService, RabbitTemplate rabbitTemplate) {
        this.userStoryService = userStoryService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public List<UserStory> getAllUserStories() {
        return userStoryService.getAllUserStories();
    }

    @PutMapping
    public void editUserStory(@RequestBody UserStory userStory) {
        userStoryService.editUserStory(userStory);
    }

    @GetMapping("/test")
    public void test() {
        rabbitTemplate.convertAndSend(exchange, routingKey, new UserStoryEvent("", new Date(), UUID.randomUUID()));
    }
}
