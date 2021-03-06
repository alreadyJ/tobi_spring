package com.splitcorp.first.factory;

import com.splitcorp.first.dao.separate.ConnectionMaker;
import com.splitcorp.first.dao.separate.DConnectionMaker;
import com.splitcorp.first.dao.separate.UserDaoSeparated;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //--> applicationContext가 사용할 설정 정보라는 표시
public class DaoFactory {
    @Bean //--> IoC 용 메소드
    public UserDaoSeparated userDaoSeparated() {
        //return new UserDaoSeparated(new DConnectionMaker());
        return new UserDaoSeparated(connectionMaker());

        // 수정자를 사용하는 경우
//        UserDaoSeparated userDaoSeparated = new UserDaoSeparated();
//        userDaoSeparated.setConnectionMaker(connectionMaker());
//        return userDaoSeparated;
    }



    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

}
