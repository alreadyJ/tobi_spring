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
}
