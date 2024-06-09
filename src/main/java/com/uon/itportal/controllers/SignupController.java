package com.uon.itservicesportal.controller;

import model.User;
import model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class SignupController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

            String encodedPassword = passwordEncoder.encode(password);
            User newUser = new User(0, username, encodedPassword, firstName, lastName, firstName + " " + lastName, email, contactNumber, roleId, "");
            User.insertUser(newUser);

            model.addAttribute("message", "User registered successfully");
            return "redirect:/login";
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred while signing up");
            return "signup";
        }
    }
}
