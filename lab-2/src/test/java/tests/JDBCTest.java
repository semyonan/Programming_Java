package tests;


import entities.CarMark;
import entities.CarModel;
import jdbc.CarMarkDaoImpl;
import jdbc.CarModelDaoImpl;
import jdbc.connectors.TestConnector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCTest {
    private static CarMarkDaoImpl carMarkDao;
    private static CarModelDaoImpl carModelDao;

    @BeforeAll
    public static void init() throws SQLException {
        var connection = DriverManager.getConnection("jdbc:h2:~/db;MODE=Mysql;INIT=RUNSCRIPT FROM 'classpath:create_tables.sql'");
        connection.close();
        carMarkDao = new CarMarkDaoImpl(new TestConnector());
        carModelDao = new CarModelDaoImpl(new TestConnector());
    }

    @Test
    public void saveAndUpdateCarMark() throws Exception {
        var carMark = new CarMark();
        carMark.setName("mark1");
        carMark.setReleaseDate(new Date(100000));

        carMarkDao.save(carMark);

        carMark.setName("mark_1");

        carMarkDao.update(carMark);

        Assertions.assertEquals("mark_1", carMarkDao.getById(carMark.getId()).getName());
    }

    @Test
    public void saveAndUpdateCarModel() throws Exception {

        var carMark1 = new CarMark();
        carMark1.setName("mark1");
        carMark1.setReleaseDate(new Date(100000));

        var carMark2 = new CarMark();
        carMark2.setName("mark2");
        carMark2.setReleaseDate(new Date(100000));

        carMark1 = carMarkDao.save(carMark1);
        carMark2 = carMarkDao.save(carMark2);

        var carModel = new CarModel();
        carModel.setLength(10);
        carModel.setWidth(5);
        carModel.setBodyType(CarModel.Body_Type.SEDAN);
        carModel.setName("car");
        carModel.setCarMark(carMark1);

        carModel = carModelDao.save(carModel);

        carModel.setCarMark(carMark2);

        carModel = carModelDao.update(carModel);

        Assertions.assertEquals("mark2", carModelDao.getById(carModel.getId()).getCarMark().getName());
    }

    @Test
    public void getAllByCarMarkId() throws Exception {
        var carMark1 = new CarMark();
        carMark1.setName("mark1");
        carMark1.setReleaseDate(new Date(100000));

        var carMark2 = new CarMark();
        carMark2.setName("mark2");
        carMark2.setReleaseDate(new Date(100000));

        carMark1 = carMarkDao.save(carMark1);
        carMark2 = carMarkDao.save(carMark2);

        var carModel1 = new CarModel();
        carModel1.setLength(10);
        carModel1.setWidth(5);
        carModel1.setBodyType(CarModel.Body_Type.SEDAN);
        carModel1.setName("car1");
        carModel1.setCarMark(carMark1);

        var carModel2 = new CarModel();
        carModel2.setLength(10);
        carModel2.setWidth(5);
        carModel2.setBodyType(CarModel.Body_Type.SEDAN);
        carModel2.setName("car2");
        carModel2.setCarMark(carMark1);

        var carModel3 = new CarModel();
        carModel3.setLength(10);
        carModel3.setWidth(5);
        carModel3.setBodyType(CarModel.Body_Type.SEDAN);
        carModel3.setName("car3");
        carModel3.setCarMark(carMark2);

        carModel1 = carModelDao.save(carModel1);
        carModel2 = carModelDao.save(carModel2);
        carModel3 = carModelDao.save(carModel3);

        Assertions.assertEquals(2, carModelDao.getAllByCarMarkId(carMark1.getId()).size());
    }
}
