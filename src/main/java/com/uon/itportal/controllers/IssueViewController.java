package com.uon.itportal.controllers;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import model.Issue;

@Controller
public class IssueViewController {

    private static Map<Integer, Issue> issueDatabase = new HashMap<>();

    static {
        try {
            // Populate the issueDatabase with data from the database
            for (Issue issue : Issue.getAllIssues()) {
                issueDatabase.put(issue.issueId(), issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/user-view-issue/{id}")
    public String userViewIssue(@PathVariable("id") int issueId, Model model) {
        Issue issue = issueDatabase.get(issueId);
        if (issue != null) {
            model.addAttribute("issue", issue);
        } else {
            model.addAttribute("error", "Issue not found.");
        }
        return "user_view_issue";
    }

    @GetMapping("/manager-view-issue/{id}")
    public String managerViewIssue(@PathVariable("id") int issueId, Model model) {
        Issue issue = issueDatabase.get(issueId);
        if (issue != null) {
            model.addAttribute("issue", issue);
        } else {
            model.addAttribute("error", "Issue not found.");
        }
        return "manager_view_issue";
    }
}
