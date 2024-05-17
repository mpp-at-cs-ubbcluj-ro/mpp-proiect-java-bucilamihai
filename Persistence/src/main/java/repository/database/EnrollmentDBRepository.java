package repository.database;

import domain.Challenge;
import domain.Child;
import domain.Enrollment;
import domain.OfficeResponsable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.HibernateUtils;
import repository.interfaces.EnrollmentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.StreamSupport;

public class EnrollmentDBRepository implements EnrollmentRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public EnrollmentDBRepository(Properties props) {
        logger.info("Initializing repository.database.EnrollmentDBRepository with properties: {} ", props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public void add(Enrollment elem) {
        logger.traceEntry("adding enrollment {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public void delete(Enrollment elem) {
        logger.traceEntry("deleting enrollment {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public void update(Enrollment elem, Long id) {
        logger.traceEntry("updating enrollment {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public Enrollment findById(Long id) {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Enrollment enrollment = session.get(Enrollment.class, id);
        session.close();
        logger.traceExit();
        return enrollment;
    }

    @Override
    public Iterable<Enrollment> findAll() {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Enrollment> query = session.createQuery("FROM Enrollment", Enrollment.class);
        List<Enrollment> enrollments = query.list();
        session.close();
        logger.traceExit();
        return enrollments;
    }

    @Override
    public Collection<Enrollment> getAll() {
        return StreamSupport.stream(findAll().spliterator(), false).toList();
    }
}
