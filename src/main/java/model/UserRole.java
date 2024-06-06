package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a user role in the system
 */
public record UserRole
(
    int roleId,
    String role
)
{
    /**
     * Retrieves all the roles from the database.
     *
     * @return A list of Role objects representing all the roles in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<UserRole> getAllUserRoles() throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var statement = connection.createStatement();

            var query = """
                    SELECT *
                    FROM [User_Role]
                    """;

            var resultSet = statement.executeQuery(query);

            var userRoles = new LinkedList<UserRole>();

            while (resultSet.next())
            {
                int roleId = resultSet.getInt("role_id");
                String role = resultSet.getString("role_name");

                UserRole userRole = new UserRole(roleId, role);
                userRoles.add(userRole);
            }

            return userRoles;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Inserts a user role in the database.
     *
     * @param _userRole The user role to be inserted.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void insertUserRole(UserRole _userRole) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    INSERT INTO [User_Role] (role_name) VALUES (?);
                    """
            );
            query.setString(1, _userRole.role());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Updates a user role in the database.
     *
     * @param _userRole The user role to be updated.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void updateUserRole(User _userRole) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    UPDATE [User_Role] SET role_name = ? WHERE role_id = ?;
                    """
            );
            query.setString(1, _userRole.role());
            query.setInt(2, _userRole.roleId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }
}