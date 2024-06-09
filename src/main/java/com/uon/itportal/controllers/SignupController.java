package com.uon.itportal.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import model.User;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String signup(Model model) {
        try {
            // Retrieve all roles to populate a dropdown in the signup form
            List<User> users = User.getAllUsers();
            model.addAttribute("users", users);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., add an error message to the model
            model.addAttribute("error", "Error retrieving user roles: " + e.getMessage());
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("contactNumber") int contactNumber,
            @RequestParam("roleId") int roleId,
            Model model) {
        try {
            // Create a new User object
            User newUser = new User(0, username, password, firstName, lastName, firstName + " " + lastName, email, contactNumber, roleId, null);
            
            // Insert the new user into the database
            User.insertUser(newUser);

            // Add a success message to the model
            model.addAttribute("message", "User successfully registered.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., add an error message to the model
            model.addAttribute("error", "Error registering user: " + e.getMessage());
        }
        return "signup";
    }
}
