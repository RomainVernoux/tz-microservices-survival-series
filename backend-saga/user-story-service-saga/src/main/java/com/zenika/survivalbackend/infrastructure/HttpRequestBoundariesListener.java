package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.infrastructure.bus.RabbitActivitiesSender;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HttpRequestBoundariesListener {

    RabbitActivitiesSender rabbitActivitiesSender;

    Logger logger = LoggerFactory.getLogger(HttpRequestBoundariesListener.class);

    public HttpRequestBoundariesListener(RabbitActivitiesSender rabbitActivitiesSender) {
        this.rabbitActivitiesSender = rabbitActivitiesSender;
    }

    @After("execution(* com.zenika.survivalbackend.exposition.*.*(..))")
    public void afterMvcRequest() {
        logger.info("Web request finished, requesting rabbit manager to handle pending activities");
        rabbitActivitiesSender.handlePendingActivities();
    }
}
