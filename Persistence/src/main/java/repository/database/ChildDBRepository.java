package repository.database;

import domain.Challenge;
import domain.Child;
import domain.OfficeResponsable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.HibernateUtils;
import repository.interfaces.ChildRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.StreamSupport;

public class ChildDBRepository implements ChildRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public ChildDBRepository(Properties props) {
        logger.info("Initializing repository.database.ChildDBRepository with properties: {} ", props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public void add(Child elem) {
        logger.traceEntry("adding child {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public void delete(Child elem) {
        logger.traceEntry("deleting child {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public void update(Child elem, Long id) {
        logger.traceEntry("updating child {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public Child findById(Long id) {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Child child = session.get(Child.class, id);
        session.close();
        logger.traceExit();
        return child;
    }

    @Override
    public Iterable<Child> findAll() {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Child> query = session.createQuery("FROM Child", Child.class);
        List<Child> children = query.list();
        session.close();
        logger.traceExit();
        return children;
    }

    @Override
    public Collection<Child> getAll() {
        return StreamSupport.stream(findAll().spliterator(), false).toList();
    }

    @Override
    public Collection<Child> getChildrenByChallengeNameAndGroupAge(String challengeName, String groupAge) {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Child> query = session.createQuery(
                "SELECT c FROM Child c " +
                        "JOIN c.enrollments e " +
                        "JOIN e.challenge ch " +
                        "WHERE ch.name = :challengeName AND ch.groupAge = :groupAge", Child.class
        );
        query.setParameter("challengeName", challengeName);
        query.setParameter("groupAge", groupAge);
        List<Child> children = query.list();
        session.close();
        logger.traceExit();
        return children;
    }

    @Override
    public Child getChildByCnp(Long cnp) {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Child> query = session.createQuery(
                "FROM Child WHERE cnp = :cnp", Child.class
        );
        query.setParameter("cnp", cnp);
        Child child = query.uniqueResult();
        session.close();
        logger.traceExit();
        return child;
    }
}
