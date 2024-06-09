package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssignStaffSuccessController {

    @GetMapping("/assign-staff-success")
    public String assignStaffSuccessMessage(Model model) {
        // Add a success message to the model
        model.addAttribute("message", "Staff successfully assigned to the issue.");
        return "assign_staff_success";
    }
}
