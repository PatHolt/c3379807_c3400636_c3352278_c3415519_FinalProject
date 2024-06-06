package model;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a comment of an issue.
 */
public record Comment
(
    int commentId,
    int issueId,
    String issueTitle,
    String issueDescription,
    int userId,
    String userFullName,
    String commentText,
    Date commentDate
)
{
    /**
     * Retrieves all the comments from the database relating to a specified issue.
     *
     * @param _issueId The ID of the issue that the comments are attached to
     * @return A list of Keyword objects representing all the comments for an issue in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<Comment> getAllCommentsById(int _issueId) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement
            (
                """
                SELECT *
                FROM [VW_Comment]
                WHERE issueId = ?;
                """
            );
            query.setInt(1, _issueId);

            var resultSet = query.executeQuery();

            var comments = new LinkedList<Comment>();

            while (resultSet.next())
            {
                int commentId = resultSet.getInt("commentId");
                int issueId = resultSet.getInt("issueId");
                String issueTitle = resultSet.getString("issueTitle");
                String issueDescription = resultSet.getString("issueDescription");
                int userId = resultSet.getInt("userId");
                String userFullName = resultSet.getString("userFullName");
                String commentText = resultSet.getString("commentText");
                Date commentDate = resultSet.getDate("commentDate");

                Comment comment = new Comment(commentId, issueId, issueTitle, issueDescription, userId, userFullName, commentText, commentDate);
                comments.add(comment);
            }

            return comments;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Inserts a comment in the database.
     *
     * @param _comment The comment to be inserted.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void insertComment(Comment _comment) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    INSERT INTO [Comment] (issue_id, user_id, comment_text, comment_date) VALUES (?,?,?,?);
                    """
            );
            query.setInt(1, _comment.issueId());
            query.setInt(2, _comment.userId());
            query.setString(3, _comment.commentText());
            query.setDate(4, (java.sql.Date) _comment.commentDate());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Updates a comment in the database.
     *
     * @param _comment The comment to be updated.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void updateComment(Comment _comment) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    UPDATE [Comment] SET issue_id = ?, user_id = ?, comment_text = ?, comment_date = ? WHERE comment_id = ?;
                    """
            );
            query.setInt(1, _comment.issueId());
            query.setInt(2, _comment.userId());
            query.setString(3, _comment.commentText());
            query.setDate(4, (java.sql.Date) _comment.commentDate());
            query.setInt(5, _comment.commentId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }
}