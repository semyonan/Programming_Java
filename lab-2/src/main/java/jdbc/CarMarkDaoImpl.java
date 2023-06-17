package jdbc;

import dao.interfaces.CarMarkDao;
import entities.CarMark;
import jdbc.connectors.Connector;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class CarMarkDaoImpl implements CarMarkDao {

    private Connector connector;
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    /**
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public CarMark save(CarMark entity) throws SQLException {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "insert into CAR_MARK (name, release_date) values (?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setDate(2, entity.getReleaseDate());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            connector.closeConnection(connection);
        }
        return entity;
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(long id) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from CAR_MARK where id=?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            connector.closeConnection(connection);
        }
    }

    /**
     * @param entity
     */
    @Override
    public void deleteByEntity(CarMark entity) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from CAR_MARK where id=?");
            statement.setLong(1, entity.getId());
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            connector.closeConnection(connection);
        }
    }

    /**
     *
     */
    @Override
    public void deleteAll() {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from CAR_MARK");
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            connector.closeConnection(connection);
        }
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public CarMark update(CarMark entity) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "update CAR_MARK set name=? , release_date=? where id=?");
            statement.setString(1, entity.getName());
            statement.setDate(2, (Date) entity.getReleaseDate());
            statement.setLong(3, entity.getId());
            statement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            connector.closeConnection(connection);
        }
        return entity;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CarMark getById(long id) {
        CarMark result = new CarMark();
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from CAR_MARK where ID=?");
            statement.setLong(1, id);
            ResultSet results = statement.executeQuery();
            results.next();
            result.setId(results.getLong("id"));
            result.setName(results.getString("name"));
            result.setReleaseDate(results.getDate("release_date"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            connector.closeConnection(connection);
        }
        return result;
    }

    /**
     * @return
     */
    @Override
    public List<CarMark> getAll() {
        List<CarMark> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from CAR_MARK");
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                CarMark carMark = new CarMark();
                carMark.setId(results.getLong("id"));
                carMark.setName(results.getString("name"));
                carMark.setReleaseDate(results.getDate("release_date"));
                result.add(carMark);
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            connector.closeConnection(connection);
        }
        return result;
    }
}
