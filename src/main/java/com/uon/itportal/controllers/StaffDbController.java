package com.uon.itportal.controllers;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import model.Issue;

@Controller
public class StaffDbController {

    @GetMapping("/staff-dashboard")
    public String managerDashboard(Model model) {
        List<Issue> issues = new ArrayList<>();

        // This is SAMPLE DATA TO TEST VIEWS REMOVE LATER
        issues.add(new Issue(1, "Issue 1", "Description 1", "Resolution 1", 101, "Category 1", 201, 
                "open", new Date(), null, 301, "Reporter 1", 401, "Assignee 1"));
        issues.add(new Issue(2, "Issue 2", "Description 2", "Resolution 2", 102, "Category 2", 202, 
                "resolved", new Date(System.currentTimeMillis() - 86400000 * 7), new 
                Date(System.currentTimeMillis() - 86400000 * 2), 302, "Reporter 2", 402, "Assignee 2"));

        // Calculate resolving time for each issue and create a DTO list
        List<IssueDTO> issueDTOs = issues.stream()
            .map(issue -> {
                Long resolvingTime = calculateResolvingTime(issue.dateReported(), issue.state());
                return new IssueDTO(issue, resolvingTime);
            })
            .collect(Collectors.toList());

        model.addAttribute("issues", issueDTOs);
        return "staff_dashboard";
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
    public record IssueDTO(Issue issue, Long resolvingTime) {}
}
