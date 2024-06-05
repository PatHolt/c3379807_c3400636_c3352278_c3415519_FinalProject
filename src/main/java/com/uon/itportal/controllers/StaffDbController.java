package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffDbController {

    @GetMapping("/staff-dashboard")
    public String staffDashboard() {
        return "staff_dashboard";
    }
}
