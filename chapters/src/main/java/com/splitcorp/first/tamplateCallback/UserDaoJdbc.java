package com.splitcorp.first.tamplateCallback;

import com.splitcorp.first.dto.User;
import com.splitcorp.first.enums.Level;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    /*이것은 익명클래스의 메소드를 한번에 표현한 것*/
    private RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setLevel(Level.valueOf(rs.getInt("level")));
        user.setLogin(rs.getInt("login"));
        user.setRecommend(rs.getInt("recommend"));
        user.setEmail(rs.getString("email"));
        return user;
    };

    public void add(final User user) {
        this.jdbcTemplate.update("insert into user(id, name, password, level, login, recommend, email)" +
                        " values(?, ?, ?, ?, ?, ?, ?)",
                user.getId(), user.getName(), user.getPassword(),
                user.getLevel(), user.getLogin(), user.getRecommend(), user.getEmail());
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from user where id = ?",
                new Object[] {id}, this.userMapper);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                "update user set name = ?, password = ?, level = ?, login = ?," +
                        "recommend = ?, email = ? where id = ?", user.getName(), user.getPassword(),
                user.getLevel(), user.getLogin(), user.getRecommend(), user.getEmail()
        );
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from user");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from user", this.userMapper);
    }
}
