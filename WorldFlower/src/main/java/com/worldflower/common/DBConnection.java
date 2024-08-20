package com.worldflower.common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {
    private static DBConnection instance;
    private DataSource dataSource;
    private Connection connection;

    private DBConnection() throws NamingException, SQLException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            this.dataSource = (DataSource) envContext.lookup("jdbc/JSPProject");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new NamingException("DB 연결 실패: " + e.getMessage());
        }
    }

    public static DBConnection getInstance() throws NamingException, SQLException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
        }
        return connection;
    }
}
