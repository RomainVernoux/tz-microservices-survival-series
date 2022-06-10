package com.zenika.survivaltester;

import com.zenika.survivaltester.model.UserStory;
import com.zenika.survivaltester.model.WorkflowRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
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

    @Autowired
    JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(SurvivalTesterApplicationTests.class);
    TestRestTemplate testRestTemplate = new TestRestTemplate();

    private final static String userStoriesUrl = "http://localhost:8080/user-stories";
    private final static String workflowRulesUrl = "http://localhost:8080/workflow-rules";


    @BeforeEach
    public void init() {
        testRestTemplate.delete(userStoriesUrl);
        testRestTemplate.delete(workflowRulesUrl);
    }

    @Test
    public void should_respect_wip_limit() throws InterruptedException          //PASS
    {
        // GIVEN
        int wipLimit = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(wipLimit + 1);
        UUID projectId = UUID.randomUUID();
        createProjectRules(projectId);

        // WHEN
        List<Callable<Void>> tasks = IntStream.range(0, wipLimit + 1).mapToObj(i -> (Callable<Void>) () -> this.createUserStoryAndSetToInProgress(projectId)).collect(Collectors.toList());
        executorService.invokeAll(tasks);

        // THEN
        UserStory[] stories = testRestTemplate.getForObject(userStoriesUrl, UserStory[].class);
        long inProgress = Arrays.stream(stories).filter(us -> us.getUserStoryStatus().equals("IN_PROGRESS")).count();
        assertThat(inProgress).isEqualTo(3);

        // AND WHEN
        List<Callable<Void>> finishTasks = IntStream.range(0, stories.length).mapToObj(i -> (Callable<Void>) () -> this.finishTask(stories[i])).collect(Collectors.toList());
        executorService.invokeAll(finishTasks);

        // THEN
        UserStory[] storiesAfterFinishing = testRestTemplate.getForObject(userStoriesUrl, UserStory[].class);
        inProgress = Arrays.stream(storiesAfterFinishing).filter(us -> us.getUserStoryStatus().equals("IN_PROGRESS")).count();
        var doneTasks = Arrays.stream(storiesAfterFinishing).filter(us -> us.getUserStoryStatus().equals("DONE")).count();
        assertThat(inProgress).isEqualTo(0);
        assertThat(doneTasks).isEqualTo(4);


    }

    private Void createUserStoryAndSetToInProgress(UUID projectId) {
        UUID userStoryId = UUID.randomUUID();
        logger.info("{} is testing batch of calls for project {} and user story {}", Thread.currentThread().getName(), projectId, userStoryId);
        testRestTemplate.postForLocation(userStoriesUrl, new UserStory(userStoryId, projectId, "Title", "Description", "TODO"));
        testRestTemplate.put(userStoriesUrl, new UserStory(userStoryId, projectId, "Title", "Description", "IN_PROGRESS"));
        return null;
    }

    private Void finishTask(UserStory story) {
        story.setUserStoryStatus("DONE");
        testRestTemplate.put(userStoriesUrl, story);
        return null;
    }

    void createProjectRules(UUID projectId) {
        logger.info("{} is configuring rules for project {}", Thread.currentThread().getName(), projectId);
        testRestTemplate.postForLocation(workflowRulesUrl, new WorkflowRule(UUID.randomUUID(), projectId, "IN_PROGRESS", 3));
    }


}
