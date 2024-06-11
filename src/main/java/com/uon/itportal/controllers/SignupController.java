package com.uon.itportal.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.User;
import model.UserRole;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        try {
            List<UserRole> roles = UserRole.getAllUserRoles();
            model.addAttribute("roles", roles);
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while loading roles");
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@RequestParam("firstname") String firstName,
                             @RequestParam("lastname") String lastName,
                             @RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("contactnumber") int contactNumber,
                             @RequestParam("password") String password,
                             @RequestParam("confirmPassword") String confirmPassword,
                             @RequestParam("roleId") int roleId,
                             Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "signup";
        }

        try {
            User existingUser = User.getUserByUsername(username);
            if (existingUser != null) {
                model.addAttribute("error", "Username already exists");
                return "signup";
            }
        
            // Directly use the plain text password
            User newUser = new User(0, username, password, firstName, lastName, firstName + " " + lastName, email, contactNumber, roleId, "");
            User.insertUser(newUser);
            return "login";
        } catch (SQLException e) {
            model.addAttribute("error", "A database error occurred: " + e.getMessage());
            return "signup";
        }
    }
}
