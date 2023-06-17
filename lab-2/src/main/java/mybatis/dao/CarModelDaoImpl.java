package mybatis.dao;

import dao.interfaces.CarModelDao;
import entities.CarModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mybatis.mappers.CarModelMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class CarModelDaoImpl implements CarModelDao {
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
     */
    @Override
    public CarModel save(CarModel entity) {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarModelMapper.class);
        carMarkMapper.insert(entity);
        closeSession(session);
        return entity;
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(long id) {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarModelMapper.class);
        carMarkMapper.delete(id);
        closeSession(session);
    }

    /**
     * @param entity
     */
    @Override
    public void deleteByEntity(CarModel entity) {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarModelMapper.class);
        carMarkMapper.delete(entity.getId());
        closeSession(session);
    }

    /**
     *
     */
    @Override
    public void deleteAll() {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarModelMapper.class);
        carMarkMapper.deleteAll();
        closeSession(session);
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public CarModel update(CarModel entity) {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarModelMapper.class);
        carMarkMapper.update(entity);
        closeSession(session);
        return entity;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CarModel getById(long id) {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarModelMapper.class);
        var entity = carMarkMapper.getById(id);
        closeSession(session);
        return entity;
    }

    /**
     * @return
     */
    @Override
    public List<CarModel> getAll() {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarModelMapper.class);
        var entities = carMarkMapper.getAll();
        closeSession(session);
        return entities;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<CarModel> getAllByCarMarkId(long id) {
        var session = getSession();
        var carMarkMapper = session.getMapper(CarModelMapper.class);
        var entities = carMarkMapper.getAllByCarMarkId(id);
        closeSession(session);
        return entities;
    }
}
