package jdbc.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnector implements Connector {
    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:h2:~/db;MODE=Mysql");
    }

    /**
     * @param connection
     */
    public void closeConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
