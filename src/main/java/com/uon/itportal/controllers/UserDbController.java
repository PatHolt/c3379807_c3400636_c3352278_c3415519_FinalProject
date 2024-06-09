package com.uon.itportal.controllers;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import model.Issue;
import model.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserDbController {

    @GetMapping("/user-dashboard")
    public String userDashboard(Model model) {
        try {
            // Retrieve all issues for the user
            List<Issue> issues = Issue.getAllIssues();

            // Calculate resolving time for each issue and create a DTO list
            List<IssueDTO> issueDTOs = issues.stream()
                .map(issue -> {
                    Long resolvingTime = calculateResolvingTime(issue.dateReported(), issue.state());
                    return new IssueDTO(issue, resolvingTime);
                })
                .collect(Collectors.toList());

            // Add the list of issues to the model
            model.addAttribute("issues", issueDTOs);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., add an error message to the model
            model.addAttribute("error", "Error retrieving issues: " + e.getMessage());
        }
        return "user_dashboard";
    }

    private Long calculateResolvingTime(Date dateReported, String state) {
        if ("Resolved".equalsIgnoreCase(state) || "Completed".equalsIgnoreCase(state)) {
            return null; // Resolved issues do not need resolving time
        }
        LocalDate reportedDate = dateReported.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Duration.between(reportedDate.atStartOfDay(), currentDate.atStartOfDay()).toDays();
    }

    // DTO class to hold issue data along with resolving time
    public static record IssueDTO(Issue issue, Long resolvingTime) {}
}
