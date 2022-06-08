package com.zenika.survivaltester;

import com.zenika.survivaltester.model.UserStory;
import com.zenika.survivaltester.model.WorkflowRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@SpringBootTest
public class SurvivalTesterApplicationTests {

    Logger logger = LoggerFactory.getLogger(SurvivalTesterApplicationTests.class);

    static JdbcTemplate jdbcTemplate = jdbcTemplate();
    static TestRestTemplate testRestTemplate = new TestRestTemplate();
    private final static String userStoriesUrl = "http://localhost:8080/user-stories";
    private final static String workflowRulesUrl = "http://localhost:8080/workflow-rules";

    ExecutorService executorService = Executors.newFixedThreadPool(4);


    @BeforeAll
    static void init() throws Exception {
        jdbcTemplate = jdbcTemplate();
        testRestTemplate.delete(userStoriesUrl);
        testRestTemplate.delete(workflowRulesUrl);
    }

    @Test
    public void should_send_batch_of_requests() throws InterruptedException          //PASS
    {
        UUID projectId = UUID.randomUUID();
        createProjectRules(projectId);
        List<Callable<Void>> tasks = IntStream.range(1, 5).mapToObj(i -> (Callable<Void>) () -> this.saveUserStoryForProject(projectId)).collect(Collectors.toList());
        executorService.invokeAll(tasks);

    }

    Void saveUserStoryForProject(UUID projectId) {
        UUID userStoryId = UUID.randomUUID();
        logger.info("{} is testing batch of calls for project {} and user story {}", Thread.currentThread().getName(), projectId, userStoryId);
        testRestTemplate.postForLocation(userStoriesUrl, new UserStory(userStoryId, projectId, "Title", "Description", "TODO"));
        testRestTemplate.put(userStoriesUrl, new UserStory(userStoryId, projectId, "Title", "Description", "IN_PROGRESS"));
        return null;
    }

    void createProjectRules(UUID projectId) {
        logger.info("{} is configuring rules for project {}", Thread.currentThread().getName(), projectId);
        testRestTemplate.postForLocation(workflowRulesUrl, new WorkflowRule(UUID.randomUUID(), projectId, "IN_PROGRESS", 3));
    }


    static JdbcTemplate jdbcTemplate() {
        // extract this 4 parameters using your own logic
        final String driverClassName = "org.postgresql.Driver";
        final String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        final String username = "postgres";
        final String password = "acomplexpassword";
        // Build dataSource manually:
        final DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url(jdbcUrl).username(username).password(password).build();

        // and make the jdbcTemplate
        return new JdbcTemplate(dataSource);
    }


}
