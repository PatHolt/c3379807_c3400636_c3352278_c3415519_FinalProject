package com.uon.itportal.controllers;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import model.Issue;

@Controller
public class UserDbController {

    @GetMapping("/user-dashboard")
    public String userDashboard(Model model) {
        try {
            List<Issue> issues = Issue.getUserCreatedIssues(301); // Replace 301 with the current user's ID

            // Calculate resolving time for each issue and create a DTO list
            List<IssueDTO> issueDTOs = issues.stream()
                .map(issue -> {
                    Long resolvingTime = calculateResolvingTime(issue.dateReported(), issue.state());
                    return new IssueDTO(issue, resolvingTime);
                })
                .collect(Collectors.toList());

            model.addAttribute("issues", issueDTOs);
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while retrieving the issues.");
        }
        return "user_dashboard";
    }

    private Long calculateResolvingTime(Date dateReported, String state) {
        if ("resolved".equals(state)) {
            return null; // Resolved issues do not need resolving time
        }
        LocalDate reportedDate = dateReported.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Duration.between(reportedDate.atStartOfDay(), currentDate.atStartOfDay()).toDays();
    }

    // DTO class to hold issue data along with resolving time
    public static record IssueDTO(Issue issue, Long resolvingTime) {}
}
