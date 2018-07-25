package com.splitcorp.first.dao.inheritance;


import com.splitcorp.first.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDaoByInheritance {

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO user (id, name, password) VALUES (?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    // 메소드의 분리 + 상속을 통한 다형성 보장
    /*
        이렇게 하면 다양하게 확장이 가능하지만, 계속 해서 DAO가 만들어질 경우 계속해서 아래의 메소드를 만들어야 한다.
    */

    // 이렇게 기능의 일부를 abstract 로 override 하도록 하는 패턴이 template method pattern
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
