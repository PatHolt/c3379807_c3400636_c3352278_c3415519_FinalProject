package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserDbController {

    @GetMapping("/user-dashboard")
    public String userDashboard() {
        return "user_dashboard";
    }
}
