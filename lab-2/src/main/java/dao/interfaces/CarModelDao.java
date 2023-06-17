package dao.interfaces;

import entities.CarMark;
import entities.CarModel;

import java.util.List;

/**
 * Dao interface for CAR_MODEL table
 */
public interface CarModelDao {
    /**
     * @param entity
     * @return
     */
    public CarModel save(CarModel entity);

    /**
     * @param id
     */
    public void deleteById(long id);

    /**
     * @param entity
     */
    public void deleteByEntity(CarModel entity);

    /**
     *
     */
    public void deleteAll();

    /**
     * @param entity
     * @return
     */
    public CarModel update(CarModel entity);

    /**
     * @param id
     * @return
     */
    public CarModel getById(long id);

    /**
     * @return
     */
    public List<CarModel> getAll();

    /**
     * @param id
     * @return
     */
    public List<CarModel> getAllByCarMarkId(long id);
}
