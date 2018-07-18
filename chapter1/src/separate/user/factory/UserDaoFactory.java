package separate.user.factory;

import separate.user.dao.ConnectionMaker;
import separate.user.dao.DConnectionMaker;
import separate.user.dao.UserDao;

import java.sql.SQLException;

public class UserDaoFactory {// 3 단계 런타임에 발생할 수 있는 오브젝트 의존 관계를 컨트롤 한다.
//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        ConnectionMaker connectionMaker = new DConnectionMaker();
//
//        UserDao userDao = new UserDao(connectionMaker);
//    }

    public UserDao UserDao() {// 4 단계 이를 팩토리로 생각한다
        ConnectionMaker connectionMaker = new DConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }

}
