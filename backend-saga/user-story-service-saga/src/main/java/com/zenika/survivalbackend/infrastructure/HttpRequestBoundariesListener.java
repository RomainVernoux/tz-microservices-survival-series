package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.infrastructure.bus.RabbitManager;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HttpRequestBoundariesListener {

    RabbitManager rabbitManager;

    Logger logger = LoggerFactory.getLogger(HttpRequestBoundariesListener.class);

    public HttpRequestBoundariesListener(RabbitManager rabbitManager) {
        this.rabbitManager = rabbitManager;
    }

    @After("execution(* com.zenika.survivalbackend.controller.*.*(..))")
    public void afterMvcRequest() {
        logger.info("Web request finished, requesting rabbit manager to handle pending activities");
        rabbitManager.handlePendingActivities();
    }
}
