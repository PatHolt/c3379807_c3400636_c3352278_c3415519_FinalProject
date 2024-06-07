
package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssignStaffSuccessController {

    @GetMapping("/assign-staff-success")
    public String newIssueSuccessMessage() {
        return "assign_staff_success";
    }
}
