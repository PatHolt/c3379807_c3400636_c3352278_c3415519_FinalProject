package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a file of issues.
 */
public record File
        (
                int fileId,
                int issueId,
                String fileName,
                String filePath
        )
{
    /**
     * Retrieves all the files from the database.
     *
     * @return A list of File objects representing all the files in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<File> getAllFiles() throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var statement = connection.createStatement();

            var query = """
                    SELECT *
                    FROM [File];
                    """;

            var resultSet = statement.executeQuery(query);

            var Files = new LinkedList<File>();

            while (resultSet.next())
            {
                int fileId = resultSet.getInt("file_id");
                int issueId = resultSet.getInt("issue_id");
                String fileName = resultSet.getString("file_name");
                String filePath = resultSet.getString("file_path");

                File newFile = new File(fileId, issueId, fileName, filePath);
                Files.add(newFile);
            }

            return Files;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Retrieves all the files related to an issue.
     *
     * @return A list of File objects representing all the files for an issue.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<File> getAllFilesByIssue(int _issueId) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    SELECT *
                    FROM [File]
                    WHERE issue_id = ?;
                    """;

            query.setInt(1, _issueId);

            var resultSet = query.executeQuery();

            var Files = new LinkedList<File>();

            while (resultSet.next())
            {
                int fileId = resultSet.getInt("file_id");
                int issueId = resultSet.getInt("issue_id");
                String fileName = resultSet.getString("file_name");
                String filePath = resultSet.getString("file_path");

                File newFile = new File(fileId, issueId, fileName, filePath);
                Files.add(newFile);
            }

            return Files;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Retrieves a file from the database.
     *
     * @param _fileId The identifier of the file to be retrieved
     * @return A file object from the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static File getFileById(int _fileId) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """

                            SELECT *
                    FROM [File]
                    WHERE file_id = ?
                    """
            );
            query.setInt(1, _fileId);

            var resultSet = query.executeQuery();

            if (resultSet .next())
            {
                int fileId = resultSet.getInt("file_id");
                int issueId = resultSet.getInt("issue_id");
                String fileName = resultSet.getString("file_name");
                String filePath = resultSet.getString("file_path");

                return new File(fileId, issueId, fileName, filePath);
            }

            return null;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Inserts a file in the database.
     *
     * @param _file The file to be inserted.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void insertFile(File _file) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    INSERT INTO [File] (issue_id, file_name, file_path) VALUES (?,?,?);
                    """
            );
            query.setInt(1, _file.issueId());
            query.setString(2, _file.fileName());
            query.setString(3, _file.filePath());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Updates a file in the database.
     *
     * @param _file The file to be updated.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void updateFile(File _file) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    UPDATE [File] SET issue_id = ?, file_name = ?, file_path = ? WHERE file_id = ?;
                    """
            );
            query.setInt(1, _file.issueId());
            query.setString(2, _file.fileName());
            query.setString(3, _file.filePath());
            query.setInt(4, _file.fileId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }
}