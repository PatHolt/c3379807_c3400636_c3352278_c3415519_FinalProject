package com.uon.itportal.controllers;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Issue;

@Controller
public class StaffDbController {
    @GetMapping("/staff-dashboard")
    public String staffDashboard(@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                 @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                 Model model) {
        try {
            List<Issue> issues = Issue.getAllIssues();

            // Filter issues by date range
            List<Issue> filteredIssues = filterIssuesByDate(issues, startDate, endDate);

            // Calculate resolving time for each issue and create a DTO list
            List<IssueDTO> issueDTOs = filteredIssues.stream()
                .map(issue -> {
                    Long resolvingTime = calculateResolvingTime(issue.dateReported(), issue.state());
                    return new IssueDTO(issue, resolvingTime);
                })
                .collect(Collectors.toList());

            model.addAttribute("issues", issueDTOs);
        } catch (SQLException e) {
            // Handle the exception
        }
        return "staff_dashboard";
    }

    private List<Issue> filterIssuesByDate(List<Issue> issues, Date startDate, Date endDate) {
        return issues.stream()
            .filter(issue -> (startDate == null || !issue.dateReported().before(startDate)) &&
                             (endDate == null || !issue.dateReported().after(endDate)))
            .collect(Collectors.toList());
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
