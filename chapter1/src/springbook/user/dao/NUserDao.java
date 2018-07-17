package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NUserDao extends UserDao {
    //이렇게 sub class 에서 구체적인 생성 방법을 결정하도록 하는 것이 factory method pattern
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
