package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffNominate {
    @GetMapping("/staff-nominate-issue")
    public String staffNominateIssue() {
        return "staff_nominate_issue";
    }
}
