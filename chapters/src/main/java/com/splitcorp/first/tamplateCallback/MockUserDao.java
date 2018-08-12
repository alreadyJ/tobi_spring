package com.splitcorp.first.tamplateCallback;

import com.splitcorp.first.dto.User;

import java.util.ArrayList;
import java.util.List;

public class MockUserDao implements UserDao {
    private List<User> users;
    private List<User> updated = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUpdated() {
        return updated;
    }

    public void setUpdated(List<User> updated) {
        this.updated = updated;
    }

    public List<User> getAll() {
        return this.users;
    }

    public void update(User user) {
        updated.add(user);
    }

    @Override
    public void add(User user) {

    }

    @Override
    public User get(String id) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int getCount() {
        return 0;
    }
}
