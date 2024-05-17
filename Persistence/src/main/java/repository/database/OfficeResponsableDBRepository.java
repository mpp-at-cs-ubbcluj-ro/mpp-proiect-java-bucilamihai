package repository.database;

import domain.OfficeResponsable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.HibernateUtils;
import repository.interfaces.OfficeResponsableRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.StreamSupport;

public class OfficeResponsableDBRepository implements OfficeResponsableRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public OfficeResponsableDBRepository(Properties props) {
        logger.info("Initializing repository.database.OfficeResponsableDBRepository with properties: {} ", props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void add(OfficeResponsable elem) {
        logger.traceEntry("adding office responsable {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public void delete(OfficeResponsable elem) {
        logger.traceEntry("deleting office responsable {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public void update(OfficeResponsable elem, Long id) {
        logger.traceEntry("updating office responsable {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public OfficeResponsable findById(Long id) {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        OfficeResponsable officeResponsable = session.get(OfficeResponsable.class, id);
        session.close();
        logger.traceExit();
        return officeResponsable;
    }

    @Override
    public Iterable<OfficeResponsable> findAll() {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<OfficeResponsable> query = session.createQuery("FROM OfficeResponsable", OfficeResponsable.class);
        List<OfficeResponsable> officeResponsables = query.list();
        session.close();
        logger.traceExit();
        return officeResponsables;
    }

    @Override
    public Collection<OfficeResponsable> getAll() {
        return StreamSupport.stream(findAll().spliterator(), false).toList();
    }

    public OfficeResponsable findByUsername(String username) {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<OfficeResponsable> query = session.createQuery(
                "FROM OfficeResponsable WHERE username = :username", OfficeResponsable.class
        );
        query.setParameter("username", username);
        OfficeResponsable officeResponsable = query.uniqueResult();
        session.close();
        logger.traceExit();
        return officeResponsable;
    }
}
