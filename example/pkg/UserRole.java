package pkg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRole {

    private int roleId;
    private String roleName;

    public UserRole(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    // Method to get all user roles from the database
    public static List<UserRole> getAllUserRoles() throws SQLException {
        List<UserRole> userRoles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Get database connection
            connection = ConfigBean.getConnection();
            String query = "SELECT * FROM USER_Role";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int roleId = resultSet.getInt("role_id");
                String roleName = resultSet.getString("role_name");
                UserRole userRole = new UserRole(roleId, roleName);
                userRoles.add(userRole);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return userRoles;
    }
}
