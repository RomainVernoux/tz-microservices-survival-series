package com.zenika.survivaltester;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

@SpringBootTest
class SurvivalTesterApplicationTests {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static TestRestTemplate testRestTemplate = new TestRestTemplate();

    private final static String baseUrl = "http://localhost:8080/user-stories";

    @BeforeAll
    static void init() {
        testRestTemplate.delete(baseUrl);
    }

    @Test
    public void should_create_story_and_set_it_in_process() {
        var list = jdbcTemplate.queryForList("select * from user_story");
        Assertions.assertThat(list).isEmpty();

        UUID projectId = UUID.randomUUID();
        UUID userStoryId = UUID.randomUUID();
        testRestTemplate.put(baseUrl, new UserStory(userStoryId, projectId, "Title", "Description", "TODO"));


        var listAfter = jdbcTemplate.queryForList("select * from user_story");
        Assertions.assertThat(listAfter).hasSize(1);
    }


    public static class UserStory {

        private UUID id;
        private UUID projectId;
        private String title;
        private String description;
        private String userStoryStatus;

        public UserStory(UUID id, UUID projectId, String title, String description, String userStoryStatus) {
            this.id = id;
            this.projectId = projectId;
            this.title = title;
            this.description = description;
            this.userStoryStatus = userStoryStatus;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public UUID getProjectId() {
            return projectId;
        }

        public void setProjectId(UUID projectId) {
            this.projectId = projectId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUserStoryStatus() {
            return userStoryStatus;
        }

        public void setUserStoryStatus(String userStoryStatus) {
            this.userStoryStatus = userStoryStatus;
        }
    }

}
