package mybatis.mappers;

import entities.CarMark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface CarMarkMapper {
    /**
     * @param id
     * @return
     */
    CarMark getCarMarkById(long id);

    /**
     * @return
     */
    List<CarMark> getCarMark();

    /**
     * @param entity
     */
    void insertCarMark(@Param("entity") CarMark entity);

    /**
     * @param id
     */
    void deleteCarMark(long id);

    /**
     *
     */
    void deleteAll();

    /**
     * @param entity
     */
    void updateCarMark(CarMark entity);
}
