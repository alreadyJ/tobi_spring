package com.splitcorp.first.dto;

import com.splitcorp.first.enums.Level;
import lombok.Data;

@Data
public class User {
    String id;
    String name;
    String password;
    Level level;
    int login;
    int recommend;
    String email;

    public User() {

    }

    public User(String id, String name, String password, Level level, int login, int recommend, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
        this.email = email;
    }

    public void upgradeLevel() {
        Level nextLevel = this.level.nextLevel();
        if (nextLevel == null) {
            throw new IllegalStateException(this.level + "은 업그레이드가 불가능합니다.");
        } else {
            this.level = nextLevel;
        }
    }
}
