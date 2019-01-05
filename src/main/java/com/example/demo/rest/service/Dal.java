package com.example.demo.rest.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ApplicationScoped
public class Dal {

    @PostConstruct
    public void initdb() throws Throwable {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jboss/datasources/ExampleDS");
        try (Connection conn = ds.getConnection()) {
            conn.createStatement()
                    .executeQuery("CREATE TABLE messages (username text, message text)");
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                //table exists
                return;
            }
            throw e;
        }
    }

    public void save(String user, String message) throws SQLException, NamingException {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jboss/datasources/ExampleDS");
        try (Connection conn = ds.getConnection()) {
            conn.createStatement().executeUpdate(String.format("INSERT INTO messages values('%s', '%s')", user, message));
        } catch (SQLException e) {
            throw e;
        }
    }

    public Map<String, String> getAll() throws SQLException, NamingException {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jboss/datasources/ExampleDS");
        Map<String, String> map = new HashMap<>();
        try (Connection conn = ds.getConnection()) {
            ResultSet resultSet = conn.createStatement().executeQuery("select * from messages");
            while (resultSet.next()) {
                map.put(
                        resultSet.getString("username"),
                        resultSet.getString("message")
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        return map;
    }
}
