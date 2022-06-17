package com.zenika.survivaltester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SurvivalTesterApplicationTests {

    TestRestTemplate testRestTemplate = new TestRestTemplate();
    ObjectMapper jsonMapper = new ObjectMapper();

    private final static String userStoriesUrl = "http://localhost:8081/user-stories";
    private final static String workflowRulesUrl = "http://localhost:8082/workflow-rules";


    @BeforeEach
    public void init() {
        deleteAllUserStories();
        deleteAllUserWorkflowRules();
    }

    @Test
    public void should_change_user_story_status() throws JsonProcessingException {
        UUID projectId = UUID.randomUUID();
        UUID userStoryId = UUID.randomUUID();
        createUserStory(userStoryId, projectId, "US1", "Desc1", "TODO");

        changeUserStoryStatus(userStoryId, projectId, "US1", "Desc2", "IN_PROGRESS");

        ArrayNode stories = testRestTemplate.getForObject(userStoriesUrl, ArrayNode.class);
        assertThat(stories.get(0).get("userStoryStatus").asText()).isEqualTo("IN_PROGRESS");
    }

    @Test
    public void should_respect_wip_limit() throws JsonProcessingException {
        UUID projectId = UUID.randomUUID();
        UUID userStory1Id = UUID.randomUUID();
        UUID userStory2Id = UUID.randomUUID();
        UUID workflowRuleId = UUID.randomUUID();
        int wipLimit = 1;
        createWipWorkflowRule(workflowRuleId, projectId, wipLimit);
        createUserStory(userStory1Id, projectId, "US1", "Desc1", "TODO");
        createUserStory(userStory2Id, projectId, "US2", "Desc2", "TODO");

        changeUserStoryStatus(userStory1Id, projectId, "US1", "Desc1", "IN_PROGRESS");
        changeUserStoryStatus(userStory2Id, projectId, "US2", "Desc2", "IN_PROGRESS");

        ArrayNode stories = testRestTemplate.getForObject(userStoriesUrl, ArrayNode.class);
        assertThat(stories.findValuesAsText("userStoryStatus"))
                .containsExactlyInAnyOrder("IN_PROGRESS", "TODO");
    }

    @Test
    public void should_respect_wip_limit_with_concurrent_access() throws InterruptedException, JsonProcessingException {
        // GIVEN
        UUID projectId = UUID.randomUUID();
        List<UUID> userStoryIds = List.of(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );
        UUID workflowRuleId = UUID.randomUUID();
        int wipLimit = 3;
        createWipWorkflowRule(workflowRuleId, projectId, wipLimit);
        createUserStory(userStoryIds.get(0), projectId, "US1", "Desc1", "TODO");
        createUserStory(userStoryIds.get(1), projectId, "US2", "Desc2", "TODO");
        createUserStory(userStoryIds.get(2), projectId, "US3", "Desc3", "TODO");
        createUserStory(userStoryIds.get(3), projectId, "US4", "Desc4", "TODO");
        ExecutorService executorService = Executors.newFixedThreadPool(wipLimit + 1);

        // WHEN
        List<Callable<Void>> tasks = IntStream.range(0, wipLimit + 1)
                .<Callable<Void>>mapToObj(i -> () -> {
                    changeUserStoryStatus(userStoryIds.get(i), projectId, "", "", "IN_PROGRESS");
                    return null;
                })
                .collect(Collectors.toList());
        executorService.invokeAll(tasks);

        // THEN
        ArrayNode stories = testRestTemplate.getForObject(userStoriesUrl, ArrayNode.class);
        assertThat(stories.findValuesAsText("userStoryStatus"))
                .containsExactlyInAnyOrder("IN_PROGRESS", "IN_PROGRESS", "IN_PROGRESS", "TODO");
    }

    private void createUserStory(UUID userStoryId, UUID projectId, String title, String description, String status)
            throws JsonProcessingException {
        ObjectNode body = jsonMapper.readValue("""
                {
                    "id": "%s",
                    "projectId": "%s",
                    "title": "%s",
                    "description": "%s",
                    "userStoryStatus": "%s"
                }
                """.formatted(userStoryId, projectId, title, description, status), ObjectNode.class);
        ResponseEntity<Void> response = testRestTemplate.postForEntity(userStoriesUrl, body, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void createWipWorkflowRule(UUID workflowRuleId, UUID projectId, int wipLimit) throws JsonProcessingException {
        ObjectNode body = jsonMapper.readValue("""
                {
                    "id": "%s",
                    "projectId": "%s",
                    "userStoryStatus": "%s",
                    "maxNumberOfUserStories": "%d"
                }
                """.formatted(workflowRuleId, projectId, "IN_PROGRESS", wipLimit), ObjectNode.class);
        ResponseEntity<Void> response = testRestTemplate.postForEntity(workflowRulesUrl, body, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void changeUserStoryStatusMono(UUID userStoryId, UUID projectId, String title, String description, String newStatus) throws JsonProcessingException {
        ObjectNode body = jsonMapper.readValue("""
                {
                    "id": "%s",
                    "projectId": "%s",
                    "title": "%s",
                    "description": "%s",
                    "userStoryStatus": "%s"
                }
                """.formatted(userStoryId, projectId, title, description, newStatus), ObjectNode.class);
        testRestTemplate.put(userStoriesUrl, body);
    }

    private void changeUserStoryStatus(UUID userStoryId, UUID projectId, String title, String description, String newStatus) throws JsonProcessingException {
        ObjectNode body = jsonMapper.readValue("""
                {
                    "newStatus": "%s"
                }
                """.formatted(newStatus), ObjectNode.class);
        ResponseEntity<Void> response = testRestTemplate.postForEntity(userStoriesUrl + "/" + userStoryId + "/change-status", body, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void deleteAllUserStories() {
        testRestTemplate.delete(userStoriesUrl);
    }

    private void deleteAllUserWorkflowRules() {
        testRestTemplate.delete(workflowRulesUrl);
    }
}
