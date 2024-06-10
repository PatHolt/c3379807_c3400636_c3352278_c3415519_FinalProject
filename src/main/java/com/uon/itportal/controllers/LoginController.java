package com.uon.itportal.controllers;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.User;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username, 
                              @RequestParam("password") String password, 
                              HttpSession session, Model model) {
        try {
            User user = userService.getUserByUsernameAndPassword(username, password);
            if (user != null) {
                session.setAttribute("user", user);
                String role = user.role();
                if (role.equals("User")) {
                    return "/user_dashboard";
                } else if (role.equals("IT Staff")) {
                    return "staff_dashboard";
                } else if (role.equals("IT Manager")) {
                    return "manager_dashboard";
                } else {
                    model.addAttribute("error", "Unknown role.");
                    return "login";
                }
            } else {
                model.addAttribute("error", "Invalid username or password.");
                return "login";
            }
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while processing your request.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
