package mybatis.mappers;

import entities.CarModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarModelMapper {
    /**
     * @param id
     * @return
     */
    CarModel getById(long id);

    /**
     * @return
     */
    List<CarModel> getAll();

    /**
     * @param id
     * @return
     */
    List<CarModel> getAllByCarMarkId(long id);

    /**
     * @param entity
     */
    void insert(@Param("entity") CarModel entity);

    /**
     * @param id
     */
    void delete(long id);

    /**
     *
     */
    void deleteAll();

    /**
     * @param entity
     */
    void update(@Param("entity") CarModel entity);

}
