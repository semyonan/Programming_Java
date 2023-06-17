package jdbc.connectors;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connector {
    public Connection getConnection() throws SQLException;
    public void closeConnection(Connection connection);
}
