package com.uon.itportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewIssueController {

    @GetMapping("/new-issue")
    public String newIssue() {
        return "new_issue";
    }

    @PostMapping("/new-issue")
    public String createIssue(@RequestParam String title,
                              @RequestParam String category,
                              @RequestParam(required = false) String otherCategory,
                              @RequestParam(required = false) String subCategory,
                              @RequestParam String description,
                              @RequestParam String contactDetails,
                              @RequestParam(required = false) boolean anonymity,
                              Model model) {
        // handle the form submission, e.g., save the issue to the database
        String selectedCategory = "Other".equals(category) ? otherCategory : category;
        
        // For demonstration purposes, just adding attributes to the model
        model.addAttribute("title", title);
        model.addAttribute("category", selectedCategory);
        model.addAttribute("subCategory", subCategory);
        model.addAttribute("description", description);
        model.addAttribute("contactDetails", contactDetails);
        model.addAttribute("anonymity", anonymity);

        return "issue_submitted"; // Return to a confirmation page or back to the dashboard
    }
}
