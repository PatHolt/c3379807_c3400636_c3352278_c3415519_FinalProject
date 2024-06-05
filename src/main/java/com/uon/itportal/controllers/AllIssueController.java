package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AllIssueController {

    @GetMapping("/all-issue")
    public String login() {
        return "all_issue";
    }
}
