package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a state of issues.
 */
public record State
(
    int stateId,
    String state
)
{
    /**
     * Retrieves all the states from the database.
     *
     * @return A list of State objects representing all the states in the database.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static List<State> getAllStates() throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var statement = connection.createStatement();

            var query = """
                    SELECT *
                    FROM [State]
                    """;

            var resultSet = statement.executeQuery(query);

            var states = new LinkedList<State>();

            while (resultSet.next())
            {
                int stateId = resultSet.getInt("state_id");
                String state = resultSet.getString("state_name");

                State newState = new State(stateId, state);
                states.add(newState);
            }

            return states;
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Inserts a state in the database.
     *
     * @param _state The state to be inserted.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void insertState(State _state) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    INSERT INTO [Issue_State] (state_name) VALUES (?);
                    """
            );
            query.setString(1, _state.state());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }

    /**
     * Updates a state in the database.
     *
     * @param _state The state to be updated.
     * @throws SQLException if there is an error in executing the SQL query.
     */
    public static void updateState(State _state) throws SQLException
    {
        var connection = ConfigBean.getConnection();

        try
        {
            var query = connection.prepareStatement(
                    """
                    UPDATE [Issue_State] SET state_name = ? WHERE state_id = ?;
                    """
            );
            query.setString(1, _state.state());
            query.setInt(2, _state.stateId());
            query.executeUpdate();
        }
        finally
        {
            connection.close();
        }
    }
}