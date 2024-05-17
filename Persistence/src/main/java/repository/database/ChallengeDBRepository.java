package repository.database;

import domain.Challenge;
import domain.OfficeResponsable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.HibernateUtils;
import repository.interfaces.ChallengeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.StreamSupport;

public class ChallengeDBRepository implements ChallengeRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public ChallengeDBRepository(Properties props) {
        logger.info("Initializing repository.database.ChallengeDBRepository with properties: {} ", props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public void add(Challenge elem) {
        logger.traceEntry("adding challenge {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public void delete(Challenge elem) {
        logger.traceEntry("deleting challenge {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public void update(Challenge elem, Long id) {
        logger.traceEntry("updating challenge {} ", elem);
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(elem);
        transaction.commit();
        session.close();
        logger.traceExit();
    }

    @Override
    public Challenge findById(Long id) {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Challenge challenge = session.get(Challenge.class, id);
        session.close();
        logger.traceExit();
        return challenge;
    }

    public Challenge findByName(String name) {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Challenge> query = session.createQuery(
                "FROM Challenge WHERE name = :name", Challenge.class
        );
        query.setParameter("name", name);
        logger.traceExit();
        session.close();
        return query.uniqueResult();
    }

    @Override
    public Iterable<Challenge> findAll() {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Challenge> query = session.createQuery("FROM Challenge", Challenge.class);
        List<Challenge> challenges = query.list();
        session.close();
        logger.traceExit();
        return challenges;
    }

    @Override
    public Collection<Challenge> getAll() {
        return StreamSupport.stream(findAll().spliterator(), false).toList();
    }

    @Override
    public Challenge getChallengeMatched(int age, String challengeName) {
        logger.traceEntry();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Query<Challenge> query = session.createQuery(
                "FROM Challenge WHERE name = :challengeName", Challenge.class
        );
        query.setParameter("challengeName", challengeName);
        List<Challenge> challenges = query.list();
        session.close();
        for (Challenge challenge : challenges) {
            if (challenge.matchAge(age)) {
                return challenge;
            }
        }
        logger.traceExit();
        return null;
    }
}
