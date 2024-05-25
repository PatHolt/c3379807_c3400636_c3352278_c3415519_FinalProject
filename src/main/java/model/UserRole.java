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
}