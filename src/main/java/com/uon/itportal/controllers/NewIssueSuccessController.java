package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewIssueSuccessController {

    @GetMapping("/new-issue-success")
    public String newIssueSuccessMessage() {
        return "new_issue_success";
    }
}
