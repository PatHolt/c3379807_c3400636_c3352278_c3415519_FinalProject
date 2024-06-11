package com.uon.itportal.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Issue;
import model.User;

@Controller
public class AssignStaffController {

    @GetMapping("/manager-assign-staff/{issueId}")
    public String showAssignStaffPage(@PathVariable int issueId, Model model) {
        try {
            // Retrieve the issue by ID
            Issue issue = Issue.getIssue(issueId);
            if (issue == null) {
                return "error"; // Handle the case where the issue is not found
            }

            // Retrieve the list of available staff
            List<User> staffList = User.getAllUsers().stream()
                .filter(user -> user.role().equalsIgnoreCase("staff")) // Assuming role 'staff' is used for IT staff
                .toList();

            // Add attributes to the model
            model.addAttribute("issue", issue);
            model.addAttribute("staffList", staffList);
        } catch (SQLException e) {
            e.printStackTrace();
            return "error"; // Handle SQL exception
        }
        
        return "assign_staff";
    }

    @PostMapping("/manager-assign-staff")
    public String assignStaff(@RequestParam int issueId,
                              @RequestParam int staffId,
                              Model model) {
        try {
            // Retrieve the issue by ID
            Issue issue = Issue.getIssue(issueId);
            if (issue == null) {
                return "error"; // Handle the case where the issue is not found
            }

            // Retrieve the staff member by ID
            User staff = User.getUserById(staffId); 

            // Update the issue with the assigned staff
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
                    staff.fullName(),
                    issue.knowledgeBaseId(),
                    issue.anonymity()
            );
            Issue.updateIssue(updatedIssue);

            // Redirect to success page with message
            return "/assign-staff-success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error"; // Handle SQL exception
        }
    }
}
