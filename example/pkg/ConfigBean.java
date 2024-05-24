package pkg;

import javax.sql.*;
import java.sql.*;
import javax.naming.*;

public class ConfigBean {

    private static final DataSource dataSource = makeDataSource();

    private static DataSource makeDataSource() {

        try {
            InitialContext ctx = new InitialContext();
            return (DataSource) ctx.lookup("java:/comp/env/jdbc/MyConnectionPool");
        } catch(NamingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}