package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a keyword.
 */
public record Keyword
(
        int keywordId,
        String keyword
)
{
    /**
     * Retrieves all the keywords from the database.
     *
     * @return A list of Keyword objects representing all the keywords in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<Keyword> getAllKeywords() throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var statement = connection.createStatement();

            var query = """
                    SELECT *
                    FROM [Issue]
                    """;

            var resultSet = statement.executeQuery(query);

            var keywords = new LinkedList<Keyword>();

            while (resultSet.next())
            {
                int keywordId = resultSet.getInt("keyword_id");
                String keyword = resultSet.getString("keyword_name");

                Keyword newKeyword = new Keyword(keywordId, keyword);
                keywords.add(newKeyword);
            }

            return keywords;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Inserts a keyword in the database.
     *
     * @param _keyword The keyword to be inserted.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void insertKeyword(Keyword _keyword) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    INSERT INTO [Keyword] (keyword_name) VALUES (?);
                    """
            );
            query.setString(1, _keyword.keyword());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Updates a keyword in the database.
     *
     * @param _keyword The keyword to be updated.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void updateKeyword(Keyword _keyword) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    UPDATE [Keyword] SET keyword_name = ? WHERE keyword_id = ?;
                    """
            );
            query.setString(1, _keyword.keyword());
            query.setInt(2, _keyword.keywordId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }
}