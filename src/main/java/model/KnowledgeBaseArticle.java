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

    /**
     * Inserts a knowledge-base article in the database.
     *
     * @param _article The knowledge-base article to be inserted.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void insertKnowledgeBaseArticle(KnowledgeBaseArticle _article) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    INSERT INTO [Knowledge_Base] (issue_id, title, description, resolution_details, date_resolved) VALUES (?,?,?,?,?);
                    """
            );
            query.setInt(1, _article.issueId());
            query.setString(2, _article.articleTitle());
            query.setString(3, _article.articleDescription());
            query.setString(4, _article.resolutionDetails());
            query.setDate(5, (java.sql.Date) _article.dateResolved());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Updates a knowledge-base article in the database.
     *
     * @param _article The knowledge-base article to be updated.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void updateKnowledgeBaseArticle(KnowledgeBaseArticle _article) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    UPDATE [Knowledge_Base] SET issue_id = ?, title = ?, description = ?, resolution_details = ?, date_resolved = ? where kb_id = ?;
                    """
            );
            query.setInt(1, _article.issueId());
            query.setString(2, _article.articleTitle());
            query.setString(3, _article.articleDescription());
            query.setString(4, _article.resolutionDetails());
            query.setDate(5, (java.sql.Date) _article.dateResolved());
            query.setInt(6, _article.knowledgeBaseId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }
}