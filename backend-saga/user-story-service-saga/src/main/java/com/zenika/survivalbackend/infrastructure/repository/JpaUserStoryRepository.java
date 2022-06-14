package com.zenika.survivalbackend.infrastructure.repository;

import com.zenika.survivalbackend.domain.userstory.UserStory;
import com.zenika.survivalbackend.domain.userstory.UserStoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaUserStoryRepository implements UserStoryRepository {
    UserStoryDao userStoryDao;

    public JpaUserStoryRepository(UserStoryDao userStoryDao) {
        this.userStoryDao = userStoryDao;
    }

    @Override
    public List<UserStory> findAll() {
        return userStoryDao.findAll();
    }

    @Override
    public UserStory find(UUID id) {
        return userStoryDao.getReferenceById(id);
    }

    @Override
    public void save(UserStory userStory) {
        userStoryDao.save(userStory);
    }
}
