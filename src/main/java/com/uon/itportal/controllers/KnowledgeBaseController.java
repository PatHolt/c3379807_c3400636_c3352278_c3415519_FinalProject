package com.uon.itportal.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Issue;

@Controller
public class KnowledgeBaseController {

    private List<Issue> knowledgeBaseArticles = new ArrayList<>();

    public KnowledgeBaseController() {
        // Mock data for demonstration
        knowledgeBaseArticles.add(new Issue(1, "Issue 1", "Description for issue 1", "Resolution for issue 1", 101, "Category 1", 201, "Resolved", new Date(System.currentTimeMillis() - 86400000 * 14), new Date(System.currentTimeMillis() - 86400000 * 7), 301, "Reporter 1", 401, "Assignee 1"));
        knowledgeBaseArticles.add(new Issue(2, "Issue 2", "Description for issue 2", "Resolution for issue 2", 102, "Category 2", 202, "Resolved", new Date(System.currentTimeMillis() - 86400000 * 20), new Date(System.currentTimeMillis() - 86400000 * 10), 302, "Reporter 2", 402, "Assignee 2"));
        knowledgeBaseArticles.add(new Issue(3, "Issue 3", "Description for issue 3", "Resolution for issue 3", 103, "Category 1", 203, "Resolved", new Date(System.currentTimeMillis() - 86400000 * 10), new Date(System.currentTimeMillis() - 86400000 * 5), 303, "Reporter 3", 403, "Assignee 3"));
        knowledgeBaseArticles.add(new Issue(4, "Issue 4", "Description for issue 4", "Resolution for issue 4", 104, "Category 3", 204, "Resolved", new Date(System.currentTimeMillis() - 86400000 * 18), new Date(System.currentTimeMillis() - 86400000 * 9), 304, "Reporter 4", 404, "Assignee 4"));
    }

    @GetMapping("/knowledge-articles")
    public String viewKnowledgeArticles(@RequestParam(value = "category", required = false) String category, Model model) {
        // Grouping articles by category
        Map<String, List<Issue>> articlesByCategory = knowledgeBaseArticles.stream()
                .collect(Collectors.groupingBy(Issue::category));

        model.addAttribute("articlesByCategory", articlesByCategory);
        model.addAttribute("selectedCategory", category);

        if (category != null && !category.isEmpty()) {
            List<Issue> filteredArticles = articlesByCategory.getOrDefault(category, new ArrayList<>());
            model.addAttribute("filteredArticles", filteredArticles);
        } else {
            model.addAttribute("filteredArticles", new ArrayList<>());
        }

        return "knowledge_articles";
    }
}
