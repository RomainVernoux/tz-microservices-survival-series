package com.zenika.survivalbackend.domain;

public class RejectedUserStoryTransitionException extends RuntimeException {

    public RejectedUserStoryTransitionException(String message) {
        super(message);
    }
}
