package com.uon.itservicesportal.controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session, Model model) {
        try {
            User user = User.getUserByUsername(username);
            if (user != null && passwordEncoder.matches(password, user.password())) {
                session.setAttribute("user", user);
                switch (user.role()) {
                    case "User":
                        return "redirect:/user/dashboard";
                    case "IT Staff":
                        return "redirect:/itstaff/dashboard";
                    case "IT Manager":
                        return "redirect:/itmanager/dashboard";
                    default:
                        model.addAttribute("error", "Unknown role");
                        return "login";
                }
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while logging in");
            return "login";
        }
    }
}
