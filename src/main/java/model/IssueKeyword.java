package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a keyword of an issue.
 */
public record IssueKeyword
(
    int issueKeywordId,
    int keywordId,
    String keyword,
    int issueId,
    String title
)
{
    /**
     * Retrieves all the keywords from the database relating to a specified issue.
     *
     * @param _issueId The ID of the issue that the keywords are attached to
     * @return A list of Keyword objects representing all the keywords for an issue in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<IssueKeyword> getAllIssueKeywordsById(int _issueId) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement
            (
                """
                SELECT *
                FROM [VW_Issue_Keyword]
                WHERE issueId = ?;
                """
            );
            query.setInt(1, _issueId);

            var resultSet = query.executeQuery();

            var issueKeywords = new LinkedList<IssueKeyword>();

            while (resultSet.next())
            {
                int issueKeywordId = resultSet.getInt("issueKeywordId");
                int keywordId = resultSet.getInt("keywordId");
                String keyword = resultSet.getString("keyword");
                int issueId = resultSet.getInt("issueId");
                String title = resultSet.getString("title");

                IssueKeyword issueKeyword = new IssueKeyword(issueKeywordId, keywordId, keyword, issueId, title);
                issueKeywords.add(issueKeyword);
            }

            return issueKeywords;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Inserts an issue keyword in the database.
     *
     * @param _issueKeyword The issue keyword to be inserted.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void insertIssueKeyword(IssueKeyword _issueKeyword) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    INSERT INTO [Issue_Keyword] (issue_id, keyword_id) VALUES (?,?);
                    """
            );
            query.setInt(1, _issueKeyword.issueId());
            query.setInt(2, _issueKeyword.keywordId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Updates an issue keyword in the database.
     *
     * @param _issueKeyword The issue keyword to be updated.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void updateIssueKeyword(IssueKeyword _issueKeyword) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    UPDATE [Issue_Keyword] SET issue_id = ?, keyword_id = ? WHERE issue_keyword_id = ?;
                    """
            );
            query.setInt(1, _issueKeyword.issueId());
            query.setInt(2, _issueKeyword.keywordId());
            query.setInt(2, _issueKeyword.issueKeywordId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }
}