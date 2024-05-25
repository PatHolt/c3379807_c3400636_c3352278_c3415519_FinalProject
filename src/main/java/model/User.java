package model;

import java.sql.*;
import java.util.*;

/**
 * Represents a user in the system.
 */
public record User
(
    int userId,
    String username,
    String password,
    String firstName,
    String lastName,
    String fullName,
    String email,
    int contactNumber,
    int roleId,
    String role

)
{
    /**
     * Retrieves all the users from the database.
     *
     * @return A list of user objects in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<User> getAllUsers() throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var statement = connection.createStatement();

            var query = """
                    SELECT *
                    FROM [VW_User];
                    """;

            var resultSet = statement.executeQuery(query);

            var users = new LinkedList<User>();

            while (resultSet.next())
            {
                int userId = resultSet.getInt("userId");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String fullName = resultSet.getString("fullName");
                String email = resultSet.getString("email");
                int contactNumber = resultSet.getInt("contactNumber");
                int roleId = resultSet.getInt("roleId");
                String role = resultSet.getString("role");

                User user = new User(userId, username, password, firstName, lastName, fullName, email, contactNumber, roleId, role);
                users.add(user);
            }

            return users;

        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Retrieves a user from the database.
     *
     * @param _userId The ID of the user to be retrieved
     * @return A user object in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static User getUserById(int _userId) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    SELECT *
                    FROM [VW_User]
                    WHERE userId = ?;
                    """
            );
            query.setInt(1, _userId);

            var resultSet = query.executeQuery();

            if (resultSet.next())
            {
                int userId = resultSet.getInt("userId");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String fullName = resultSet.getString("fullName");
                String email = resultSet.getString("email");
                int contactNumber = resultSet.getInt("contactNumber");
                int roleId = resultSet.getInt("roleId");
                String role = resultSet.getString("role");

                return new User(userId, username, password, firstName, lastName, fullName, email, contactNumber, roleId, role);
            }

            return null;

        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Retrieves a user from the database.
     *
     * @param _username The username of the user to be retrieved
     * @return A user object in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static User getUserByUsername(int _username) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    SELECT *
                    FROM [VW_User]
                    WHERE username = ?;
                    """
            );
            query.setInt(1, _username);

            var resultSet = query.executeQuery();

            if (resultSet.next())
            {
                int userId = resultSet.getInt("userId");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String fullName = resultSet.getString("fullName");
                String email = resultSet.getString("email");
                int contactNumber = resultSet.getInt("contactNumber");
                int roleId = resultSet.getInt("roleId");
                String role = resultSet.getString("role");

                return new User(userId, username, password, firstName, lastName, fullName, email, contactNumber, roleId, role);
            }

            return null;

        }
        finally
        {
            connection.close();
        }
    }
}
