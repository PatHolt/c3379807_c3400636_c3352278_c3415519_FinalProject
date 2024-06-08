package com.uon.itportal.controllers;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.Issue;
import model.Keyword;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewIssueController {

    @GetMapping("/new-issue")
    public String newIssueForm(Model model) {
        try {
            // Retrieve all users and keywords to populate the form
            List<User> users = User.getAllUsers();
            List<Keyword> keywords = Keyword.getAllKeywords();

            model.addAttribute("users", users);
            model.addAttribute("keywords", keywords);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., add an error message to the model
            model.addAttribute("error", "Error retrieving users or keywords: " + e.getMessage());
        }
        return "new_issue";
    }

    @PostMapping("/new-issue")
    public String submitNewIssue(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("reportedById") int reportedById,
            @RequestParam("assignedToId") int assignedToId,
            @RequestParam(value = "keywords", required = false) List<Integer> keywordIds,
            Model model) {
        try {
            // Create a new Issue object
            Issue newIssue = new Issue(
                    0, // issueId will be generated by the database
                    title,
                    description,
                    null, // resolutionDetails will be null initially
                    categoryId,
                    null, // category will be set by the database view
                    1, // stateId for "New"
                    "New", // initial state
                    new Date(), // dateReported is the current date
                    null, // dateResolved is null initially
                    reportedById,
                    null, // reportedByFullName will be set by the database view
                    assignedToId,
                    null // assignedToFullName will be set by the database view
            );

            // Insert the new issue into the database
            Issue.insertIssue(newIssue);

            // Insert keywords if provided
            if (keywordIds != null) {
                for (int keywordId : keywordIds) {
                    IssueKeyword issueKeyword = new IssueKeyword(0, keywordId, null, newIssue.issueId(), null);
                    IssueKeyword.insertIssueKeyword(issueKeyword);
                }
            }

            // Add a success message to the model
            model.addAttribute("message", "Issue successfully reported.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., add an error message to the model
            model.addAttribute("error", "Error reporting issue: " + e.getMessage());
        }
        return "new_issue_success";
    }
}
