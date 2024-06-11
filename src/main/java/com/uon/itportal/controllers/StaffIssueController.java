package com.uon.itportal.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Issue;

@Controller
public class StaffIssueController {

    private static Map<Integer, Issue> issueDatabase = new HashMap<>();

    static {
        // Populate with some mock data
        issueDatabase.put(1, new Issue(1, "Issue 1", "Description for issue 1", "Resolution 1", 101, "Category 1", 201, "New", new Date(System.currentTimeMillis() - 86400000 * 7), null, 301, "Reporter 1", 401, null, 0, false));
        issueDatabase.put(2, new Issue(2, "Issue 2", "Description for issue 2", "Resolution 2", 102, "Category 2", 202, "Resolved", new Date(System.currentTimeMillis() - 86400000 * 14), new Date(System.currentTimeMillis() - 86400000 * 7), 302, "Reporter 2", 402, "Assignee 2", 0, false));
    }

    @GetMapping("/staff-nominate-issue/{id}")
    public String staffNominateIssues(@PathVariable("id") int issueId, Model model) {
        Issue issue = issueDatabase.get(issueId);
        if (issue != null) {
            issue = new Issue(issue.issueId(), issue.title(), issue.description(), issue.resolutionDetails(), issue.categoryId(), issue.category(), issue.stateId(), "In Progress", issue.dateReported(), issue.dateResolved(), issue.reportedById(), issue.reportedByFullName(), issue.assignedToId(), "Current Staff", issueId, false); // Update state and assignee
            issueDatabase.put(issueId, issue);
        }
        model.addAttribute("issue", issue);
        return "staff_nominate_issue";
    }

    @GetMapping("/staff-view-issue/{id}")
    public String staffViewIssue(@PathVariable("id") int issueId, Model model) {
        Issue issue = issueDatabase.get(issueId);
        model.addAttribute("issue", issue);
        return "staff_view_issue";
    }

    @PostMapping("/staff-view-issue/{id}/comment")
    public String addComment(@PathVariable("id") int issueId, @RequestParam("comment") String comment, Model model) {
        // For simplicity, we'll mock the comment handling
        Issue issue = issueDatabase.get(issueId);
        // Here you would add the comment to the issue (not implemented in this mock)
        model.addAttribute("issue", issue);
        return "staff_view_issue";
    }

    @PostMapping("/staff-view-issue/{id}/resolve")
    public String resolveIssue(@PathVariable("id") int issueId, @RequestParam("resolution") String resolution, Model model) {
        Issue issue = issueDatabase.get(issueId);
        if (issue != null) {
            issue = new Issue(issue.issueId(), issue.title(), issue.description(), resolution, issue.categoryId(), issue.category(), issue.stateId(), "resolved", issue.dateReported(), new Date(), issue.reportedById(), issue.reportedByFullName(), issue.assignedToId(), issue.assignedToFullName(), issueId, false);
            issueDatabase.put(issueId, issue);
        }
        model.addAttribute("issue", issue);
        return "staff_view_issue";
    }

    @PostMapping("/staff-view-issue/{id}/update-state")
    public String updateIssueState(@PathVariable("id") int issueId, @RequestParam("state") String state, Model model) {
        Issue issue = issueDatabase.get(issueId);
        if (issue != null) {
            issue = new Issue(issue.issueId(), issue.title(), issue.description(), issue.resolutionDetails(), issue.categoryId(), issue.category(), issue.stateId(), state, issue.dateReported(), issue.dateResolved(), issue.reportedById(), issue.reportedByFullName(), issue.assignedToId(), issue.assignedToFullName(), issueId, false);
            issueDatabase.put(issueId, issue);
        }
        model.addAttribute("issue", issue);
        return "staff_view_issue";
    }

    @PostMapping("/staff-view-issue/{id}/finalize")
    public String finalizeIssue(@PathVariable("id") int issueId, Model model) {
        Issue issue = issueDatabase.get(issueId);
        if (issue != null) {
            issue = new Issue(issue.issueId(), issue.title(), issue.description(), issue.resolutionDetails(), issue.categoryId(), issue.category(), issue.stateId(), "Completed", issue.dateReported(), new Date(), issue.reportedById(), issue.reportedByFullName(), issue.assignedToId(), issue.assignedToFullName(), issueId, false);
            issueDatabase.put(issueId, issue);
        }
        model.addAttribute("issue", issue);
        return "staff_view_issue";
    }
}

