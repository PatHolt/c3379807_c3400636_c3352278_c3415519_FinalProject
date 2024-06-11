package com.uon.itportal.controllers;
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
    public UserService userService;

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
                switch (user.role()) {
                    case "3":
                        return "manager_dashboard";
                    case "2":
                        return "staff_dashboard";
                    case "1":
                        return "user_dashboard";
                    default:
                        model.addAttribute("error", "Invalid role");
                        return "login";
                }
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "login";
        }
    }
}
