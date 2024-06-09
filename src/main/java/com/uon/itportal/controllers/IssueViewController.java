package com.uon.itportal.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import model.Issue;

@Controller
public class IssueViewController {

    private static Map<Integer, Issue> issueDatabase = new HashMap<>();

    static {
        // Populate with some mock data
        issueDatabase.put(1, new Issue(1, "Issue 1", "Description for issue 1", "Resolution 1", 101, "Category 1", 201, "New", new Date(System.currentTimeMillis() - 86400000 * 7), null, 301, "Reporter 1", 401, null, 0, false));
        issueDatabase.put(2, new Issue(2, "Issue 2", "Description for issue 2", "Resolution 2", 102, "Category 2", 202, "Resolved", new Date(System.currentTimeMillis() - 86400000 * 14), new Date(System.currentTimeMillis() - 86400000 * 7), 302, "Reporter 2", 402, "Assignee 2", 0, false));
    }

    @GetMapping("/user-view-issue/{id}")
    public String userViewIssue(@PathVariable("id") int issueId, Model model) {
        Issue issue = issueDatabase.get(issueId);
        model.addAttribute("issue", issue);
        return "user_view_issue";
    }

    @GetMapping("/manager-view-issue/{id}")
    public String managerViewIssue(@PathVariable("id") int issueId, Model model) {
        Issue issue = issueDatabase.get(issueId);
        model.addAttribute("issue", issue);
        return "manager_view_issue";
    }
}
