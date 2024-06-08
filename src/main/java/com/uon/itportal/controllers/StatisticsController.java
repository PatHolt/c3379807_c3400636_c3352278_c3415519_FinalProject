package com.uon.itportal.controllers;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import model.Issue;

@Controller
public class StatisticsController {

    @GetMapping("/view-statistics")
    public String viewStatistics(Model model) {
        try {
            // Retrieve all issues from the database
            List<Issue> issues = Issue.getAllIssues();

            // Number of issues in each category
            Map<String, Long> issuesPerCategory = issues.stream()
                    .collect(Collectors.groupingBy(Issue::category, Collectors.counting()));

            // Number of issues in each status
            Map<String, Long> issuesPerStatus = issues.stream()
                    .collect(Collectors.groupingBy(Issue::state, Collectors.counting()));

            // Number of issues each IT staff is working on
            Map<String, Long> issuesPerStaff = issues.stream()
                    .filter(issue -> issue.assignedToFullName() != null)
                    .collect(Collectors.groupingBy(Issue::assignedToFullName, Collectors.counting()));

            // Average time for an issue to get resolved in the last 30 days
            long currentTime = System.currentTimeMillis();
            List<Issue> resolvedIssues = issues.stream()
                    .filter(issue -> "Resolved".equalsIgnoreCase(issue.state()) && issue.dateResolved() != null)
                    .filter(issue -> (currentTime - issue.dateResolved().getTime()) <= 86400000L * 30)
                    .collect(Collectors.toList());
            double averageResolutionTime = resolvedIssues.stream()
                    .mapToLong(issue -> (issue.dateResolved().getTime() - issue.dateReported().getTime()) / 86400000L)
                    .average()
                    .orElse(0);

            // Top 5 longest unresolved issues
            List<Issue> top5LongestUnresolvedIssues = issues.stream()
                    .filter(issue -> !"Resolved".equalsIgnoreCase(issue.state()))
                    .sorted((i1, i2) -> Long.compare(i2.dateReported().getTime(), i1.dateReported().getTime()))
                    .limit(5)
                    .collect(Collectors.toList());

            // Add the statistics to the model
            model.addAttribute("issuesPerCategory", issuesPerCategory);
            model.addAttribute("issuesPerStatus", issuesPerStatus);
            model.addAttribute("issuesPerStaff", issuesPerStaff);
            model.addAttribute("averageResolutionTime", averageResolutionTime);
            model.addAttribute("top5LongestUnresolvedIssues", top5LongestUnresolvedIssues);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., add an error message to the model
            model.addAttribute("error", "Error retrieving statistics: " + e.getMessage());
        }
        return "view_statistics";
    }
}
