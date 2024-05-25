package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a category of issues.
 */
public record Category
(
        int categoryId,
        String category
)
{
    /**
     * Retrieves all the categories from the database.
     *
     * @return A list of Category objects representing all the categories in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<Category> getAllCategories() throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var statement = connection.createStatement();

            var query = """
                    SELECT *
                    FROM [Category]
                    """;

            var resultSet = statement.executeQuery(query);

            var categories = new LinkedList<Category>();

            while (resultSet.next())
            {
                int categoryId = resultSet.getInt("category_id");
                String category = resultSet.getString("category_name");

                Category newCategory = new Category(categoryId, category);
                categories.add(newCategory);
            }

            return categories;
        }
        finally
        {
            connection.close();
        }
    }
}