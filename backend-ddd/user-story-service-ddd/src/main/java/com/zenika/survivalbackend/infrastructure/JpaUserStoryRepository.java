package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.UserStory;
import com.zenika.survivalbackend.domain.UserStoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaUserStoryRepository implements UserStoryRepository {

    private final JpaUserStoryDao jpaUserStoryDAO;

    public JpaUserStoryRepository(JpaUserStoryDao jpaUserStoryDAO) {
        this.jpaUserStoryDAO = jpaUserStoryDAO;
    }

    @Override
    public List<UserStory> findAll() {
        return jpaUserStoryDAO.findAll();
    }

    @Override
    public UserStory find(UUID id) {
        return jpaUserStoryDAO.getReferenceById(id);
    }

    @Override
    public void save(UserStory userStory) {
        jpaUserStoryDAO.save(userStory);
    }
}
