package com.uon.itportal.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.User;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               @RequestParam("email") String email,
                               @RequestParam("contactNumber") int contactNumber,
                               @RequestParam("role") String role,
                               Model model) {
        try {
            if (userService.isUsernameTaken(username)) {
                model.addAttribute("error", "Username is already taken.");
                return "signup";
            }

            int roleId = getRoleId(role); // Assuming a method to get the role ID based on the role name
            User newUser = new User(0, username, password, firstName, lastName, firstName + " " + lastName, email, contactNumber, roleId, role);
            userService.insertUser(newUser);
            return "redirect:/login";
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while processing your request.");
            return "signup";
        }
    }

    private int getRoleId(String role) {
        return switch (role) {
            case "User" -> 1;
                
            case "IT Staff" -> 2;
            case "IT Manager" -> 3;
            default -> 0;
                
        };
    }
}
