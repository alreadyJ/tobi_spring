package com.splitcorp.first.tamplateCallback;

import com.splitcorp.first.dto.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
}
