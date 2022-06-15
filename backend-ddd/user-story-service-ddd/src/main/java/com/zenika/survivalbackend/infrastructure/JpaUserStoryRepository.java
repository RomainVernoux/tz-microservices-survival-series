package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.UserStory;
import com.zenika.survivalbackend.domain.UserStoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaUserStoryRepository implements UserStoryRepository {

    private final UserStoryDao userStoryDAO;

    public JpaUserStoryRepository(UserStoryDao userStoryDAO) {
        this.userStoryDAO = userStoryDAO;
    }

    @Override
    public List<UserStory> findAll() {
        return userStoryDAO.findAll();
    }

    @Override
    public UserStory find(UUID id) {
        return userStoryDAO.getReferenceById(id);
    }

    @Override
    public void save(UserStory userStory) {
        userStoryDAO.save(userStory);
    }
}
