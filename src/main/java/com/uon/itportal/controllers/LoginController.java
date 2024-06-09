package com.uon.itportal.controllers;

import java.sql.SQLException;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Returns the login view
    }

    @PostMapping("/login")
    public String loginSubmit(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        try {
            // Authenticate the user by username
            User user = User.getUserByUsername(username);
            if (user != null && user.password().equals(password)) {
                // User is authenticated
                model.addAttribute("user", user);
                return "redirect:/dashboard"; // Redirect to a dashboard or home page
            } else {
                // Authentication failed
                model.addAttribute("error", "Invalid username or password.");
                return "login";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., add an error message to the model
            model.addAttribute("error", "Error during authentication: " + e.getMessage());
            return "login";
        }
    }
}
