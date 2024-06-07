package com.uon.itportal.controllers;

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

    private static Map<Integer, Issue> issueDatabase = new HashMap<>();

    static {
        // Populate with some mock data
        issueDatabase.put(1, new Issue(1, "Issue 1", "Description for issue 1", "Resolution 1", 101, "Category 1", 201, "New", new Date(System.currentTimeMillis() - 86400000 * 7), null, 301, "Reporter 1", 401, null));
        issueDatabase.put(2, new Issue(2, "Issue 2", "Description for issue 2", "Resolution 2", 102, "Category 1", 202, "Resolved", new Date(System.currentTimeMillis() - 86400000 * 14), new Date(System.currentTimeMillis() - 86400000 * 7), 302, "Reporter 2", 402, "Assignee 2"));
        issueDatabase.put(3, new Issue(3, "Issue 3", "Description for issue 3", "Resolution 3", 103, "Category 3", 203, "In Progress", new Date(System.currentTimeMillis() - 86400000 * 3), null, 303, "Reporter 3", 403, null));
        issueDatabase.put(4, new Issue(4, "Issue 4", "Description for issue 4", "Resolution 4", 104, "Category 4", 204, "Waiting on Third Party", new Date(System.currentTimeMillis() - 86400000 * 5), null, 304, "Reporter 4", 404, null));
        issueDatabase.put(5, new Issue(5, "Issue 5", "Description for issue 5", "Resolution 5", 105, "Category 5", 205, "Waiting on Reporter", new Date(System.currentTimeMillis() - 86400000 * 10), null, 305, "Reporter 5", 405, null));
    }

    @GetMapping("/view-statistic")
    public String viewStatistics(Model model) {
        // Number of issues in each category
        Map<String, Long> issuesPerCategory = issueDatabase.values().stream()
            .collect(Collectors.groupingBy(Issue::category, Collectors.counting()));

        // Number of issues in each status
        Map<String, Long> issuesPerStatus = issueDatabase.values().stream()
            .collect(Collectors.groupingBy(Issue::state, Collectors.counting()));

        // Number of issues each IT staff is working on
        Map<String, Long> issuesPerStaff = issueDatabase.values().stream()
            .filter(issue -> issue.assignedToFullName() != null)
            .collect(Collectors.groupingBy(Issue::assignedToFullName, Collectors.counting()));

        // Average time for an issue to get resolved in the last 30 days
        long currentTime = System.currentTimeMillis();
        List<Issue> resolvedIssues = issueDatabase.values().stream()
            .filter(issue -> issue.state().equals("Resolved") && issue.dateResolved() != null)
            .filter(issue -> (currentTime - issue.dateResolved().getTime()) <= 86400000L * 30)
            .collect(Collectors.toList());
        double averageResolutionTime = resolvedIssues.stream()
            .mapToLong(issue -> (issue.dateResolved().getTime() - issue.dateReported().getTime()) / 86400000L)
            .average()
            .orElse(0);

        // Top 5 longest unresolved issues
        List<Issue> top5LongestUnresolvedIssues = issueDatabase.values().stream()
            .filter(issue -> !issue.state().equals("Resolved"))
            .sorted((i1, i2) -> Long.compare(i2.dateReported().getTime(), i1.dateReported().getTime()))
            .limit(5)
            .collect(Collectors.toList());

        model.addAttribute("issuesPerCategory", issuesPerCategory);
        model.addAttribute("issuesPerStatus", issuesPerStatus);
        model.addAttribute("issuesPerStaff", issuesPerStaff);
        model.addAttribute("averageResolutionTime", averageResolutionTime);
        model.addAttribute("top5LongestUnresolvedIssues", top5LongestUnresolvedIssues);

        return "view_statistic";
    }
}
