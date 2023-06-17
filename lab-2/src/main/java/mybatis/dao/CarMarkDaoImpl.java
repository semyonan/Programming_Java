package mybatis.dao;

import dao.interfaces.CarMarkDao;
import entities.CarMark;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mybatis.mappers.CarMarkMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.List;

/**
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class CarMarkDaoImpl implements CarMarkDao {
    /**
     * @return
     */
    private SqlSession getSession() {
        return sessionFactory.openSession();
    }
    private SqlSessionFactory sessionFactory;
    public void setSessionFactory(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @param sqlSession
     */
    private void closeSession(SqlSession sqlSession) {
        if (sqlSession == null) {
            return;
        }

        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public CarMark save(CarMark entity) throws SQLException {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarMarkMapper.class);
        carMarkMapper.insertCarMark(entity);
        closeSession(session);
        return entity;
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(long id) {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarMarkMapper.class);
        carMarkMapper.deleteCarMark(id);
        closeSession(session);
    }

    /**
     * @param entity
     */
    @Override
    public void deleteByEntity(CarMark entity) {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarMarkMapper.class);
        carMarkMapper.deleteCarMark(entity.getId());
        closeSession(session);
    }

    /**
     *
     */
    @Override
    public void deleteAll() {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarMarkMapper.class);
        carMarkMapper.deleteAll();
        closeSession(session);
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public CarMark update(CarMark entity) {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarMarkMapper.class);
        carMarkMapper.updateCarMark(entity);
        closeSession(session);
        return entity;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CarMark getById(long id) {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarMarkMapper.class);
        var entity = carMarkMapper.getCarMarkById(id);
        closeSession(session);
        return entity;
    }

    /**
     * @return
     */
    @Override
    public List<CarMark> getAll() {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarMarkMapper.class);
        var entities = carMarkMapper.getCarMark();
        closeSession(session);
        return entities;
    }
}
