package com.splitcorp.first.tamplateCallback;

import com.splitcorp.first.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    /*이것은 익명클래스의 메소드를 한번에 표현한 것*/
    private RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId("id");
        user.setName("name");
        user.setPassword("password");
        return user;
    };

    public void add(final User user) {
        this.jdbcTemplate.update("insert into user(id, name, password) values(?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from user where id = ?",
                new Object[] {id}, this.userMapper);
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
