package com.zenika.survivalbackend.exposition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zenika.survivalbackend.application.WorkflowService;
import com.zenika.survivalbackend.domain.UserStoryStatus;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class UserStoryEventHandler {

    private WorkflowService workflowService;
    private ObjectMapper jsonMapper = new ObjectMapper();

    public UserStoryEventHandler(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    public void onEvent(byte[] eventData) throws IOException {
        ObjectNode event = jsonMapper.readValue(eventData, ObjectNode.class);
        UUID projectId = UUID.fromString(event.get("projectId").asText());
        UUID userStoryId = UUID.fromString(event.get("userStoryId").asText());
        UserStoryStatus oldStatus = UserStoryStatus.valueOf(event.get("oldStatus").asText());
        UserStoryStatus newStatus = UserStoryStatus.valueOf(event.get("newStatus").asText());
        workflowService.validateUserStoryTransition(projectId, userStoryId, oldStatus, newStatus);
    }
}
