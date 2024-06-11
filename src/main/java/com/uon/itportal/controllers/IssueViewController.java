package com.uon.itportal.controllers;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import model.Issue;

@Controller
public class IssueViewController {

    @GetMapping("/user-view-issue/{id}")
    public String userViewIssue(@PathVariable("id") int issueId, Model model) {
        try {
            Issue issue = Issue.getIssue(issueId);
            if (issue != null) {
                model.addAttribute("issue", issue);
                return "user_view_issue";
            } else {
                // Handle issue not found
                return "error";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/manager-view-issue/{id}")
    public String managerViewIssue(@PathVariable("id") int issueId, Model model) {
        try {
            Issue issue = Issue.getIssue(issueId);
            if (issue != null) {
                model.addAttribute("issue", issue);
                return "manager_view_issue";
            } else {
                // Handle issue not found
                return "error";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
