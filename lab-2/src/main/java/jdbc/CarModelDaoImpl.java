package jdbc;

import dao.interfaces.CarModelDao;
import entities.CarModel;
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
public class CarModelDaoImpl implements CarModelDao {

    private Connector connector;
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public CarModel save(CarModel entity) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "insert into CAR_MODEL (name, length, width, body_type, car_mark_id) values (?, ?, ?, ?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getLength());
            statement.setInt(3, entity.getWidth());
            statement.setString(4, entity.getBodyType().name());
            statement.setLong(5, entity.getCarMark().getId());
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
            PreparedStatement statement = connection.prepareStatement("delete from CAR_MODEL where id=?");
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
    public void deleteByEntity(CarModel entity) {
        deleteById(entity.getId());
    }

    /**
     *
     */
    @Override
    public void deleteAll() {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("delete from CAR_MODEL");
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
    public CarModel update(CarModel entity) {
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "update CAR_MODEL set name=? , length=?, width=?, body_type=?, car_mark_id=? where id=?");
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getLength());
            statement.setInt(3, entity.getWidth());
            statement.setString(4, entity.getBodyType().name());
            statement.setLong(5, entity.getCarMark().getId());
            statement.setLong(6, entity.getId());
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
    public CarModel getById(long id) {
        CarModel result = new CarModel();
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from CAR_MODEL where ID=?");
            statement.setLong(1, id);
            ResultSet results = statement.executeQuery();
            results.next();
            result.setId(results.getLong("id"));
            result.setName(results.getString("name"));
            result.setLength(results.getInt("length"));
            result.setWidth(results.getInt("width"));
            result.setBodyType(CarModel.Body_Type.valueOf(results.getString("body_type")));
            result.setCarMark(new CarMarkDaoImpl(connector).getById(results.getLong("car_mark_id")));
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
    public List<CarModel> getAll() {
        List<CarModel> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from CAR_MODEL");
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                CarModel carModel = new CarModel();
                carModel.setId(results.getLong("id"));
                carModel.setName(results.getString("name"));
                carModel.setLength(results.getInt("length"));
                carModel.setWidth(results.getInt("width"));
                carModel.setBodyType(CarModel.Body_Type.valueOf(results.getString("body_type")));
                carModel.setCarMark(new CarMarkDaoImpl(connector).getById(results.getLong("car_mark_id")));
                result.add(carModel);
            }
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            connector.closeConnection(connection);
        }
        return result;
    }


    /**
     * @param id
     * @return
     */
    @Override
    public List<CarModel> getAllByCarMarkId(long id) {
        List<CarModel> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from CAR_MODEL where CAR_MARK_ID=?");
            statement.setLong(1, id);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                CarModel carModel = new CarModel();
                carModel.setId(results.getLong("id"));
                carModel.setName(results.getString("name"));
                carModel.setLength(results.getInt("length"));
                carModel.setWidth(results.getInt("width"));
                carModel.setBodyType(CarModel.Body_Type.valueOf(results.getString("body_type")));
                carModel.setCarMark(new CarMarkDaoImpl(connector).getById(results.getLong("car_mark_id")));
                result.add(carModel);
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
