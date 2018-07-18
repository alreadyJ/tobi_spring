package separate.user.dao;

import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDao {
    //private SimpleConnectionMaker simpleConnectionMaker; // 분리의 1단계
    private ConnectionMaker connectionMaker; // 분리의 2 단계

    public UserDao() {
        //simpleConnectionMaker = new SimpleConnectionMaker(); // 분리의 1단계, 클래스를 분리
        // 위의 코드는 결합도를 높임, 이유는 회사마다 다른 Connection 메이커를 사용할 수 있기 때문이다.
         connectionMaker = new DConnectionMaker();  // 분리의 2단계 인터페이스의 활용
        // 하지만 이 것도 문제가 DConnectionMaker라는 것이 명시 되기 때문에

        //의존 관계 검색을 이용하는 UserDao 생성자
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        //this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
    }

    public UserDao(ConnectionMaker connectionMaker) { // 분리의 3단계 생성자를 수정
        this.connectionMaker = connectionMaker;
    }

    public void add() throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();
        //,,,
        return null;
    }

}
