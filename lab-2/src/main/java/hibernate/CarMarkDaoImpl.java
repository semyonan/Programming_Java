package hibernate;

import dao.interfaces.CarMarkDao;
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
public class CarMarkDaoImpl implements CarMarkDao {
    static {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(CarMark.class);
        configuration.addAnnotatedClass(CarModel.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }
    private static SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        CarMarkDaoImpl.sessionFactory = sessionFactory;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public CarMark save(CarMark entity) {
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
        CarMark entity = session.load(CarMark.class,id);
        session.delete(entity);
        trans.commit();
        session.close();
    }

    /**
     * @param entity
     */
    @Override
    public void deleteByEntity(CarMark entity) {
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
        for (CarMark element : getAll()) {
            deleteByEntity(element);
        }
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public CarMark update(CarMark entity) {
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
    public CarMark getById(long id) {
        var session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        var entity = (CarMark) session.
                createNamedQuery("CarMark.findById", CarMark.class).
                setParameter("id", (int)id).getSingleResult();
        trans.commit();
        session.close();
        return entity;
    }

    /**
     * @return
     */
    @Override
    public List<CarMark> getAll() {
        var session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        var entities = session.createQuery("from CarMark e").list();
        trans.commit();
        session.close();
        return entities;
    }
}
