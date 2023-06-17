import dao.interfaces.CarMarkDao;
import dao.interfaces.CarModelDao;
import entities.CarMark;
import entities.CarModel;
import jdbc.CarMarkDaoImpl;
import jdbc.CarModelDaoImpl;
import jdbc.connectors.MainConnector;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        List<CarMark> carMarkList = new ArrayList<>();
        List<CarModel> carModelList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {

            var carMark = new CarMark();
            carMark.setName("mark" + i);
            carMark.setReleaseDate(new Date(i*100000));
            carMarkList.add(carMark);

            var carModel = new CarModel();
            carModel.setLength(i*10);
            carModel.setWidth(i*5);
            carModel.setBodyType(CarModel.Body_Type.SEDAN);
            carModel.setName("car" + i);
            carModel.setCarMark(carMark);
            carModelList.add(carModel);
        }

        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        var sessionFactory = new SqlSessionFactoryBuilder().build(reader);

        System.out.println("WITH JDBC");
        save(carMarkList, carModelList, new CarModelDaoImpl(new MainConnector()), new CarMarkDaoImpl(new MainConnector()));
        delete(carMarkList, carModelList, new CarModelDaoImpl(new MainConnector()), new CarMarkDaoImpl(new MainConnector()));

        System.out.println("WITH HIBERNATE");
        save(carMarkList, carModelList, new hibernate.CarModelDaoImpl(), new hibernate.CarMarkDaoImpl());
        delete(carMarkList, carModelList, new hibernate.CarModelDaoImpl(), new hibernate.CarMarkDaoImpl());

        System.out.println("WITH MYBATIS");
        save(carMarkList, carModelList, new mybatis.dao.CarModelDaoImpl(sessionFactory), new mybatis.dao.CarMarkDaoImpl(sessionFactory));
        delete(carMarkList, carModelList, new mybatis.dao.CarModelDaoImpl(sessionFactory), new mybatis.dao.CarMarkDaoImpl(sessionFactory));
    }

    public static void save(List<CarMark> carMarkList, List<CarModel> carModelList, CarModelDao carModelDao, CarMarkDao carMarkDao) throws SQLException {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            carMarkDao.save(carMarkList.get(i));
            carModelDao.save(carModelList.get(i));
        }

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;

        System.out.println("Прошло времени на добавлние, мс: " + elapsed);
    }

    public static void delete(List<CarMark> carMarkList, List<CarModel> carModelList, CarModelDao carModelDao, CarMarkDao carMarkDao) throws SQLException {
        long start = System.currentTimeMillis();

        carMarkDao.deleteAll();
        carModelDao.deleteAll();

        long finish = System.currentTimeMillis();
        long elapsed = finish - start;

        System.out.println("Прошло времени на удаление, мс: " + elapsed);
    }


}
