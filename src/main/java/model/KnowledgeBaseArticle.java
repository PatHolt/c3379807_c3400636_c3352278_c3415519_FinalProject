package model;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a knowledge base article in the system.
 */
public record KnowledgeBaseArticle
(
    int knowledgeBaseId,
    int issueId,
    String issueTitle,
    String issueDescription,
    String articleTitle,
    String articleDescription,
    String resolutionDetails,
    Date dateResolved
)
{
    /**
     * Retrieves all the knowledge base articles from the database.
     *
     * @return A list of KnowledgeBaseArticle objects representing all the knowledge base articles in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<KnowledgeBaseArticle> getAllKnowledgeBaseArticles() throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var statement = connection.createStatement();

            var query = """
                    SELECT *
                    FROM [VW_Knowledge_Base]
                    """;

            var resultSet = statement.executeQuery(query);

            var knowledgeBaseArticles = new LinkedList<KnowledgeBaseArticle>();

            while (resultSet.next())
            {
                int knowledgeBaseId = resultSet.getInt("knowledgeBaseId");
                int issueId = resultSet.getInt("issueId");
                String issueTitle = resultSet.getString("issueTitle");
                String issueDescription = resultSet.getString("issueDescription");
                String articleTitle = resultSet.getString("articleTitle");
                String articleDescription = resultSet.getString("articleDescription");
                String resolutionDetails = resultSet.getString("resolutionDetails");
                Date dateResolved = resultSet.getDate("dateResolve");

                KnowledgeBaseArticle knowledgeBaseArticle = new KnowledgeBaseArticle(knowledgeBaseId, issueId, issueTitle, issueDescription, articleTitle, articleDescription, resolutionDetails, dateResolved);
                knowledgeBaseArticles.add(knowledgeBaseArticle);
            }

            return knowledgeBaseArticles;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Retrieves all the knowledge base articles from the database.
     *
     * @return A list of KnowledgeBaseArticle objects representing all the knowledge base articles in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<KnowledgeBaseArticle> getAllKnowledgeBaseArticlesById(int _issueId) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                """
                SELECT *
                FROM [VW_Knowledge_Base]
                WHERE issueId = ?;
                """
            );
            query.setInt(1, _issueId);

            var resultSet = query.executeQuery();

            var knowledgeBaseArticles = new LinkedList<KnowledgeBaseArticle>();

            while (resultSet.next())
            {
                int knowledgeBaseId = resultSet.getInt("knowledgeBaseId");
                int issueId = resultSet.getInt("issueId");
                String issueTitle = resultSet.getString("issueTitle");
                String issueDescription = resultSet.getString("issueDescription");
                String articleTitle = resultSet.getString("articleTitle");
                String articleDescription = resultSet.getString("articleDescription");
                String resolutionDetails = resultSet.getString("resolutionDetails");
                Date dateResolved = resultSet.getDate("dateResolve");

                KnowledgeBaseArticle knowledgeBaseArticle = new KnowledgeBaseArticle(knowledgeBaseId, issueId, issueTitle, issueDescription, articleTitle, articleDescription, resolutionDetails, dateResolved);
                knowledgeBaseArticles.add(knowledgeBaseArticle);
            }

            return knowledgeBaseArticles;
        }
        finally
        {
            connection.close();
        }
    }
}