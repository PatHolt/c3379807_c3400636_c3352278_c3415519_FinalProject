package model;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents an issue in the system.
 */
public record Issue
(
     int issueId,
     String title,
     String description,
     String resolutionDetails,
     int categoryId,
     String category,
     int stateId,
     String state,
     Date dateReported,
     Date dateResolved,
     int reportedById,
     String reportedByFullName,
     int assignedToId,
     String assignedToFullName

)
{
    /**
     * Retrieves all the issues from the database.
     *
     * @return A list of Issue objects representing all the issues in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<Issue> getAllIssues() throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var statement = connection.createStatement();

            var query = """
                    SELECT *
                    FROM [VW_Issue]
                    """;

            var resultSet = statement.executeQuery(query);

            var issues = new LinkedList<Issue>();

            while (resultSet.next())
            {
                int issueId = resultSet.getInt("issueId");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String resolutionDetails = resultSet.getString("resolutionDetails");
                int categoryId = resultSet.getInt("categoryId");
                String category = resultSet.getString("category");
                int stateId = resultSet.getInt("stateId");
                String state = resultSet.getString("state");
                Date dateReported = resultSet.getDate("dateReported");
                Date dateResolved = resultSet.getDate("dateResolved");
                int reportedById = resultSet.getInt("reportedById");
                String reportedByFullName = resultSet.getString("reportedByFullName");
                int assignedToId = resultSet.getInt("assignedToId");
                String assignedToFullName = resultSet.getString("assignedToFullName");

                Issue issue = new Issue(issueId, title, description, resolutionDetails, categoryId, category, stateId, state, dateReported, dateResolved, reportedById, reportedByFullName, assignedToId, assignedToFullName);
                issues.add(issue);
            }

            return issues;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Retrieves all the issues from the database that belong to the specified category.
     *
     * @param _categoryId The ID of the category that the issues are in
     * @return A list of Issue objects representing all the issues in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<Issue> getAllIssuesByCategory(int _categoryId) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement
                    (
                            """
                            SELECT *
                            FROM [VW_Issue]
                            WHERE categoryId = ?;
                            """
                    );
            query.setInt(1, _categoryId);

            var resultSet = query.executeQuery();

            var issues = new LinkedList<Issue>();

            while (resultSet.next())
            {
                int issueId = resultSet.getInt("issueId");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String resolutionDetails = resultSet.getString("resolutionDetails");
                int categoryId = resultSet.getInt("categoryId");
                String category = resultSet.getString("category");
                int stateId = resultSet.getInt("stateId");
                String state = resultSet.getString("state");
                Date dateReported = resultSet.getDate("dateReported");
                Date dateResolved = resultSet.getDate("dateResolved");
                int reportedById = resultSet.getInt("reportedById");
                String reportedByFullName = resultSet.getString("reportedByFullName");
                int assignedToId = resultSet.getInt("assignedToId");
                String assignedToFullName = resultSet.getString("assignedToFullName");

                Issue issue = new Issue(issueId, title, description, resolutionDetails, categoryId, category, stateId, state, dateReported, dateResolved, reportedById, reportedByFullName, assignedToId, assignedToFullName);
                issues.add(issue);
            }

            return issues;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Retrieves all the issues from the database that are assigned to a user.
     *
     * @param _userId The ID of the assigned user whose issues are to be retrieved
     * @return A list of Issue objects representing all the issues that are assigned to a specific user in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<Issue> getUserAssignedIssues(int _userId) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement
            (
                """
                SELECT *
                FROM [VW_Issue]
                WHERE userId = ?;
                """
            );
            query.setInt(1, _userId);

            var resultSet = query.executeQuery();

            var issues = new LinkedList<Issue>();

            while (resultSet.next())
            {
                int issueId = resultSet.getInt("issueId");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String resolutionDetails = resultSet.getString("resolutionDetails");
                int categoryId = resultSet.getInt("categoryId");
                String category = resultSet.getString("category");
                int stateId = resultSet.getInt("stateId");
                String state = resultSet.getString("state");
                Date dateReported = resultSet.getDate("dateReported");
                Date dateResolved = resultSet.getDate("dateResolved");
                int reportedById = resultSet.getInt("reportedById");
                String reportedByFullName = resultSet.getString("reportedByFullName");
                int assignedToId = resultSet.getInt("assignedToId");
                String assignedToFullName = resultSet.getString("assignedToFullName");

                Issue issue = new Issue(issueId, title, description, resolutionDetails, categoryId, category, stateId, state, dateReported, dateResolved, reportedById, reportedByFullName, assignedToId, assignedToFullName);
                issues.add(issue);
            }

            return issues;

        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Retrieves all the issues from the database that are created by a user.
     *
     * @param _userId The ID of the created user whose issues are to be retrieved
     * @return A list of Issue objects representing all the issues that are assigned to a specific user in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<Issue> getUserCreatedIssues(int _userId) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement
            (
                """
                SELECT *
                FROM [VW_Issue]
                WHERE userId = ?;
                """
            );
            query.setInt(1, _userId);

            var resultSet = query.executeQuery();

            var issues = new LinkedList<Issue>();

            while (resultSet.next())
            {
                int issueId = resultSet.getInt("issueId");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String resolutionDetails = resultSet.getString("resolutionDetails");
                int categoryId = resultSet.getInt("categoryId");
                String category = resultSet.getString("category");
                int stateId = resultSet.getInt("stateId");
                String state = resultSet.getString("state");
                Date dateReported = resultSet.getDate("dateReported");
                Date dateResolved = resultSet.getDate("dateResolved");
                int reportedById = resultSet.getInt("reportedById");
                String reportedByFullName = resultSet.getString("reportedByFullName");
                int assignedToId = resultSet.getInt("assignedToId");
                String assignedToFullName = resultSet.getString("assignedToFullName");

                Issue issue = new Issue(issueId, title, description, resolutionDetails, categoryId, category, stateId, state, dateReported, dateResolved, reportedById, reportedByFullName, assignedToId, assignedToFullName);
                issues.add(issue);
            }

            return issues;

        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Retrieves a single issue from the database.
     *
     * @param _issueId The ID of the issue to be retrieved
     * @return A single Issue object from the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static Issue getIssue(int _issueId) throws SQLException {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                            SELECT *
                            FROM [VW_Issue]
                            WHERE issueId = ?;
                            """
            );
            query.setInt(1, _issueId);

            var resultSet = query.executeQuery();

            if (resultSet.next())
            {
                int issueId = resultSet.getInt("issueId");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String resolutionDetails = resultSet.getString("resolutionDetails");
                int categoryId = resultSet.getInt("categoryId");
                String category = resultSet.getString("category");
                int stateId = resultSet.getInt("stateId");
                String state = resultSet.getString("state");
                Date dateReported = resultSet.getDate("dateReported");
                Date dateResolved = resultSet.getDate("dateResolved");
                int reportedById = resultSet.getInt("reportedById");
                String reportedByFullName = resultSet.getString("reportedByFullName");
                int assignedToId = resultSet.getInt("assignedToId");
                String assignedToFullName = resultSet.getString("assignedToFullName");

                return new Issue(issueId, title, description, resolutionDetails, categoryId, category, stateId, state, dateReported, dateResolved, reportedById, reportedByFullName, assignedToId, assignedToFullName);
            }

            return null;
        } finally {
            connection.close();
        }
    }
}