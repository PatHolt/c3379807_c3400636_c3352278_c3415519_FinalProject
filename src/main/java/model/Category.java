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
                    FROM [Issue_Category]
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

    /**
     * Inserts a category in the database.
     *
     * @param _category The category to be inserted.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void insertCategory(Category _category) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    INSERT INTO [Issue_Category] (category_name) VALUES (?);
                    """
            );
            query.setString(1, _category.category());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Updates a category in the database.
     *
     * @param _category The category to be updated.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void updateCategory(Category _category) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    UPDATE [Issue_Category] SET category_name = ? WHERE category_id = ?;
                    """
            );
            query.setString(1, _category.category());
            query.setInt(2, _category.categoryId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }
}