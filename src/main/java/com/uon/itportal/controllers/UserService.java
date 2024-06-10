package com.uon.itportal.controllers;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import model.User;

@Service
public class UserService {

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        User user = User.getUserByUsername(username);
        if (user != null && user.password().equals(password)) {
            return user;
        }
        return null;
    }
    public boolean isUsernameTaken(String username) throws SQLException {
        return User.getUserByUsername(username) != null;
    }

    public void insertUser(User user) throws SQLException {
        User.insertUser(user);
    }
}
