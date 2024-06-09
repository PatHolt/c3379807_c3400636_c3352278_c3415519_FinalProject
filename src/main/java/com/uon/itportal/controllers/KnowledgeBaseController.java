package com.uon.itportal.controllers;

import java.util.ArrayList;
import java.util.Date;
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

    private List<KnowledgeBaseArticle> knowledgeBaseArticles = new ArrayList<>();
    private Map<Integer, List<String>> articleComments = new HashMap<>();

    public KnowledgeBaseController() {
        // Mock data for demonstration
        knowledgeBaseArticles.add(new KnowledgeBaseArticle(1, 101, "Issue 1", "Description for issue 1", "Article 1", "Description of Article 1", "Resolution for issue 1", new Date()));
        knowledgeBaseArticles.add(new KnowledgeBaseArticle(2, 102, "Issue 2", "Description for issue 2", "Article 2", "Description of Article 2", "Resolution for issue 2", new Date()));

        // Initialize comment lists for each article
        knowledgeBaseArticles.forEach(article -> articleComments.put(article.knowledgeBaseId(), new ArrayList<>()));
    }

    @GetMapping("/knowledge-articles")
    public String viewKnowledgeArticles(@RequestParam(value = "category", required = false) String category,
                                        @RequestParam(value = "role", required = false, defaultValue = "user") String role,
                                        Model model) {
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

        return "knowledge_articles";
    }

    @GetMapping("/knowledge-article/{id}")
    public String viewKnowledgeArticle(@PathVariable("id") int articleId,
                                       @RequestParam(value = "role", required = false, defaultValue = "user") String role,
                                       Model model) {
        KnowledgeBaseArticle article = knowledgeBaseArticles.stream()
                .filter(a -> a.knowledgeBaseId() == articleId)
                .findFirst()
                .orElse(null);

        if (article != null) {
            model.addAttribute("article", article);
            model.addAttribute("comments", articleComments.get(articleId));
            model.addAttribute("role", role);
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
