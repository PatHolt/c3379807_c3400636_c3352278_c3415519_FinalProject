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
    public static User getUserByUsername(String _username) throws SQLException
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
            query.setString(1, _username);

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
     * Inserts a user in the database.
     *
     * @param _user The user to be inserted.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void insertUser(User _user) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    INSERT INTO [User] (username, password, first_name, last_name, email, contact_number, role_id) VALUES (?,?,?,?,?,?,?);
                    """
            );
            query.setString(1, _user.username());
            query.setString(2, _user.password());
            query.setString(3, _user.firstName());
            query.setString(4, _user.lastName());
            query.setString(5, _user.email());
            query.setInt(6, _user.contactNumber());
            query.setInt(7, _user.roleId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Updates a user in the database.
     *
     * @param _user The user to be updated.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void updateUser(User _user) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    UPDATE [User] SET username = ?, password = ?, first_name = ?, last_name = ?, email = ?, contact_number = ?, role_id = ? WHERE user_id = ?;
                    """
            );
            query.setString(1, _user.username());
            query.setString(2, _user.password());
            query.setString(3, _user.firstName());
            query.setString(4, _user.lastName());
            query.setString(5, _user.email());
            query.setInt(6, _user.contactNumber());
            query.setInt(7, _user.roleId());
            query.setInt(8, _user.userId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }
}
