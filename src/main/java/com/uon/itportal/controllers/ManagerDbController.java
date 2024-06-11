package com.uon.itportal.controllers;

import java.sql.SQLException;
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
public class ManagerDbController {

    @GetMapping("/manager-dashboard")
    public String managerDashboard(Model model) {
        List<Issue> issues = null;
        try {
            issues = Issue.getAllIssues(); // Fetch issues from the database
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception (you may want to log this or show an error message to the user)
        }

        if (issues != null) {
            // Calculate resolving time for each issue and create a DTO list
            List<IssueDTO> issueDTOs = issues.stream()
                .map(issue -> {
                    Long resolvingTime = calculateResolvingTime(issue.dateReported(), issue.dateResolved(), issue.state());
                    return new IssueDTO(issue, resolvingTime);
                })
                .collect(Collectors.toList());

            model.addAttribute("issues", issueDTOs);
        } else {
            // Handle the case where issues could not be fetched (optional)
            model.addAttribute("issues", List.of());
        }
        
        return "manager_dashboard";
    }

    private Long calculateResolvingTime(Date dateReported, Date dateResolved, String state) {
        if ("resolved".equals(state) && dateResolved != null) {
            LocalDate reportedDate = dateReported.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate resolvedDate = dateResolved.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return Duration.between(reportedDate.atStartOfDay(), resolvedDate.atStartOfDay()).toDays();
        }
        return null;
    }

    // DTO class to hold issue data along with resolving time
    public record IssueDTO(Issue issue, Long resolvingTime) {}
}
