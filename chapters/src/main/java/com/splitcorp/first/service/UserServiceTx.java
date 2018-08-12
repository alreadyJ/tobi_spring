package com.splitcorp.first.service;

import com.splitcorp.first.dto.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class UserServiceTx implements UserService {
    UserService userService;
    PlatformTransactionManager platformTransactionManager;

    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = this.platformTransactionManager
                .getTransaction(new DefaultTransactionDefinition());
        try {
            userService.upgradeLevels();

            this.platformTransactionManager.commit(status);
        } catch (RuntimeException e) {
            this.platformTransactionManager.rollback(status);
            throw e;
        }
    }
}
