package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewIssueController {

    @GetMapping("/new-issue")
    public String newIssue() {
        return "new_issue";
    }
}
