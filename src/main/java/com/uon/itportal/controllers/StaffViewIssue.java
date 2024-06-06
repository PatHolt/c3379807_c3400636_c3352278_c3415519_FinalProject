package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffViewIssue {

    @GetMapping("/staff-view-issue")
    public String login() {
        return "staff_view_issue";
    }
}
