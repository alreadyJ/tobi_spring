package com.splitcorp.first.dao.separate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends UserDaoSeparated {

    @Override
    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("delete from user");
        return ps;
    }

    /*
    * 이런식으로 작성할 경우 UserDao의 JDBC try/catch/finally 블록과 변하는 prepared statement를 담고 있는
    * 서브 클래스들이 이미 클래스 레벨에서 컴파일 시점에 이미 그 관계가 결정되어 있다 따라서 그 관계 에 대한 유연성이 떨어진다.
    * */
}
