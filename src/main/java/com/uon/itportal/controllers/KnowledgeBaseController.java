package com.uon.itportal.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.KnowledgeBaseArticle;

@Controller
public class KnowledgeBaseController {

    private Map<Integer, List<String>> articleComments = new HashMap<>();

    public KnowledgeBaseController() {
        // Initialize comment lists for each article
        try {
            List<KnowledgeBaseArticle> knowledgeBaseArticles = KnowledgeBaseArticle.getAllKnowledgeBaseArticles();
            knowledgeBaseArticles.forEach(article -> articleComments.put(article.knowledgeBaseId(), new ArrayList<>()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/knowledge-articles")
    public String viewKnowledgeArticles(@RequestParam(value = "category", required = false) String category,
                                        @RequestParam(value = "role", required = false, defaultValue = "user") String role,
                                        Model model) {
        try {
            List<KnowledgeBaseArticle> knowledgeBaseArticles = KnowledgeBaseArticle.getAllKnowledgeBaseArticles();
            // Grouping articles by issue category
            Map<String, List<KnowledgeBaseArticle>> articlesByCategory = knowledgeBaseArticles.stream()
                    .collect(Collectors.groupingBy(KnowledgeBaseArticle::issueTitle));

            model.addAttribute("articlesByCategory", articlesByCategory);
            model.addAttribute("selectedCategory", category);
            model.addAttribute("role", role);

            List<KnowledgeBaseArticle> filteredArticles;
            if (category != null && !category.isEmpty()) {
                filteredArticles = articlesByCategory.getOrDefault(category, new ArrayList<>());
            } else {
                filteredArticles = knowledgeBaseArticles;
            }
            model.addAttribute("filteredArticles", filteredArticles);
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }

        return "knowledge_articles";
    }

    @GetMapping("/knowledge-article/{id}")
    public String viewKnowledgeArticle(@PathVariable("id") int articleId,
                                       @RequestParam(value = "role", required = false, defaultValue = "user") String role,
                                       Model model) {
        try {
            KnowledgeBaseArticle article = KnowledgeBaseArticle.getAllKnowledgeBaseArticles().stream()
                    .filter(a -> a.knowledgeBaseId() == articleId)
                    .findFirst()
                    .orElse(null);

            if (article != null) {
                model.addAttribute("article", article);
                model.addAttribute("comments", articleComments.get(articleId));
                model.addAttribute("role", role);
            } else {
                return "error"; // Handle the case where the article is not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }

        return "knowledge_article_detail";
    }

    @PostMapping("/knowledge-article/{id}/comment")
    public String addComment(@PathVariable("id") int articleId,
                             @RequestParam String comment,
                             @RequestParam(value = "role", required = false, defaultValue = "user") String role,
                             Model model) {
        List<String> comments = articleComments.get(articleId);
        if (comments != null) {
            comments.add(comment);
        }

        return "redirect:/knowledge-article/" + articleId + "?role=" + role;
    }
}
