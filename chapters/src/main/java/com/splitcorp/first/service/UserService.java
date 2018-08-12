package com.splitcorp.first.service;

import com.splitcorp.first.dto.User;

public interface UserService {
    void add(User user);
    void upgradeLevels();
}
