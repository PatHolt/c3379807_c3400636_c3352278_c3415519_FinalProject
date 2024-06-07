package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssignStaffController {
    
    @GetMapping("/manager-assign-staff")
    public String assignStaff() {
        return "manager_assign_staff";
    }

}
