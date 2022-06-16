package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.UserStory;
import com.zenika.survivalbackend.domain.UserStoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaUserStoryRepository implements UserStoryRepository {

    private final JpaUserStoryDao jpaUserStoryDao;

    public JpaUserStoryRepository(JpaUserStoryDao jpaUserStoryDao) {
        this.jpaUserStoryDao = jpaUserStoryDao;
    }

    @Override
    public List<UserStory> findAll() {
        return jpaUserStoryDao.findAll();
    }

    @Override
    public UserStory find(UUID id) {
        return jpaUserStoryDao.getReferenceById(id);
    }

    @Override
    public void save(UserStory userStory) {
        jpaUserStoryDao.save(userStory);
    }
}
