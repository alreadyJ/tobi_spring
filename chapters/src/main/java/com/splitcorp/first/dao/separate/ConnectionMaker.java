package com.splitcorp.first.dao.separate;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;


public interface ConnectionMaker {

    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
