package hibernate;

import dao.interfaces.CarModelDao;
import entities.CarMark;
import entities.CarModel;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 *
 */
public class CarModelDaoImpl implements CarModelDao {

    static {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(CarMark.class);
        configuration.addAnnotatedClass(CarModel.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }
    private static SessionFactory sessionFactory;

    /**
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        CarModelDaoImpl.sessionFactory = sessionFactory;
    }
    @Override
    public CarModel save(CarModel entity) {
        var session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.save(entity);
        trans.commit();
        session.close();
        return entity;
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(long id) {
        var session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        CarModel entity = session.load(CarModel.class,id);
        session.delete(entity);
        trans.commit();
        session.close();
    }

    /**
     * @param entity
     */
    @Override
    public void deleteByEntity(CarModel entity) {
        var session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.delete(entity);
        trans.commit();
        session.close();
    }

    /**
     *
     */
    @Override
    public void deleteAll() {
        for (CarModel element : getAll()) {
            deleteByEntity(element);
        }
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public CarModel update(CarModel entity) {
        var session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.update(entity);
        trans.commit();
        session.close();
        return entity;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CarModel getById(long id) {
        var session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        CarModel entity = (CarModel) session.
                getNamedQuery("CarModel.findById").
                setParameter("id", id).uniqueResult();
        trans.commit();
        session.close();
        return entity;
    }

    /**
     * @return
     */
    @Override
    public List<CarModel> getAll() {
        var session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        var entities = session.
                createQuery("from CarModel e").list();
        trans.commit();
        session.close();
        return entities;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<CarModel> getAllByCarMarkId(long id) {
        var session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        var entities = session.
                getNamedQuery("CarModel.findAllByCarMarkId").
                setParameter("car_mark_id", id).list();
        trans.commit();
        session.close();
        return entities;
    }
}
