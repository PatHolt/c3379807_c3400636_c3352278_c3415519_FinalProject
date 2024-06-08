package com.uon.itportal.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Issue;
import model.User;

@Controller
public class AssignStaffController {

    @GetMapping("/manager-assign-staff")
    public String assignStaffForm(Model model) {
        try {
            // Retrieve all issues and IT staff to populate the form
            List<Issue> issues = Issue.getAllIssues();
            List<User> staff = User.getAllUsers().stream()
                                   .filter(user -> user.role().equalsIgnoreCase("IT Staff"))
                                   .toList();

            model.addAttribute("issues", issues);
            model.addAttribute("staff", staff);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., add an error message to the model
            model.addAttribute("error", "Error retrieving issues or staff: " + e.getMessage());
        }
        return "manager_assign_staff";
    }

    @PostMapping("/manager-assign-staff")
    public String assignStaff(
            @RequestParam("issueId") int issueId,
            @RequestParam("staffId") int staffId,
            Model model) {
        try {
            // Retrieve the issue and update its assigned staff member
            Issue issue = Issue.getIssue(issueId);
            if (issue != null) {
                Issue updatedIssue = new Issue(
                        issue.issueId(),
                        issue.title(),
                        issue.description(),
                        issue.resolutionDetails(),
                        issue.categoryId(),
                        issue.category(),
                        issue.stateId(),
                        issue.state(),
                        issue.dateReported(),
                        issue.dateResolved(),
                        issue.reportedById(),
                        issue.reportedByFullName(),
                        staffId,
                        null // assignedToFullName will be set by the database view
                );
                Issue.updateIssue(updatedIssue);

                // Add a success message to the model
                model.addAttribute("message", "Staff successfully assigned to the issue.");
            } else {
                model.addAttribute("error", "Issue not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., add an error message to the model
            model.addAttribute("error", "Error assigning staff: " + e.getMessage());
        }
        return "assign_staff_success";
    }
}
