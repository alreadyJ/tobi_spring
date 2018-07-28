package com.splitcorp.first.factory;

import com.splitcorp.first.dao.separate.ConnectionMaker;
import com.splitcorp.first.dao.separate.CountingConnectionMaker;
import com.splitcorp.first.dao.separate.DConnectionMaker;
import com.splitcorp.first.dao.separate.UserDaoSeparated;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {
    @Bean
    public UserDaoSeparated userDaoSeparated() {
        return new UserDaoSeparated(connectionMaker());
    }
    // 메소드 이름 틀려서 고생함..
    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new DConnectionMaker();
    }
}
