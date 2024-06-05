package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerDbController {

    @GetMapping("/manager-dashboard")
    public String managerDashboard() {
        return "manager_dashboard";
    }
}
