package com.zenika.survivalbackend.infrastructure;

import com.zenika.survivalbackend.domain.UserStoryChangeStatusSaga;
import com.zenika.survivalbackend.domain.UserStoryChangeStatusSagaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaUserStoryChangeStatusSagaRepository implements UserStoryChangeStatusSagaRepository {

    UserStoryChangeStatusSagaDao userStoryChangeStatusSagaDao;

    public JpaUserStoryChangeStatusSagaRepository(UserStoryChangeStatusSagaDao userStoryChangeStatusSagaDao) {
        this.userStoryChangeStatusSagaDao = userStoryChangeStatusSagaDao;
    }

    @Override
    public Optional<UserStoryChangeStatusSaga> findActiveSagaForUserStory(UUID userStoryId) {
        return userStoryChangeStatusSagaDao.findFirstByUserStoryIdAndAndWorkflowRuleResponseDateIsNull(userStoryId);
    }

    @Override
    public void save(UserStoryChangeStatusSaga userStoryChangeStatusSaga) {
        userStoryChangeStatusSagaDao.save(userStoryChangeStatusSaga);
    }
}
