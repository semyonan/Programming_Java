package dao.interfaces;

import entities.CarMark;

import java.sql.SQLException;
import java.util.List;

/**
 * Dao interface for CAR_MARK table
 */
public interface CarMarkDao {
    /**
     * @param entity
     * @return
     * @throws SQLException
     */
    public CarMark save(CarMark entity) throws SQLException;

    /**
     * @param id
     */
    public void deleteById(long id);

    /**
     * @param entity
     */
    public void deleteByEntity(CarMark entity);

    /**
     *
     */
    public void deleteAll();

    /**
     * @param entity
     * @return
     */
    public CarMark update(CarMark entity);

    /**
     * @param id
     * @return
     */
    public CarMark getById(long id);

    /**
     * @return
     */
    public List<CarMark> getAll();
}
