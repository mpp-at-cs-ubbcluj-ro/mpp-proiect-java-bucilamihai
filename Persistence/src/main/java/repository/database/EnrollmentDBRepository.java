package repository.database;

import domain.Challenge;
import domain.Child;
import domain.Enrollment;
import domain.OfficeResponsable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO enrollments (child_id, challenge_id) " +
                        "VALUES (?, ?)")) {
            preparedStatement.setLong(1, elem.getChild().getId());
            preparedStatement.setLong(2, elem.getChallenge().getId());
            int result = preparedStatement.executeUpdate();
            logger.trace("added {} instances ", result);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Enrollment elem) {
        logger.traceEntry("deleting enrollment {} ", elem);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM enrollments " +
                        "WHERE ID = ?")) {
            preparedStatement.setLong(1, elem.getId());
            int result = preparedStatement.executeUpdate();
            logger.trace("deleted {} instances ", result);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Enrollment elem, Long id) {
        logger.traceEntry("updating enrollment {} ", elem);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE enrollments " +
                        "SET child_id = ?, challenge_id = ? " +
                        "WHERE id = ?")) {
            preparedStatement.setLong(1, elem.getChild().getId());
            preparedStatement.setLong(2, elem.getChallenge().getId());
            preparedStatement.setLong(3, id);
            int result = preparedStatement.executeUpdate();
            logger.trace("updated {} instances ", result);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public Enrollment findById(Long id) {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM enrollments " +
                        "WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                Long childId = resultSet.getLong(2);
                Child child = null;
                try (PreparedStatement preparedStatementChild = connection.prepareStatement(
                        "SELECT * FROM children " +
                                "WHERE id = ?")) {
                    preparedStatementChild.setLong(1, childId);
                    ResultSet resultSetChild = preparedStatementChild.executeQuery();
                    if(resultSetChild.next())
                    {
                        Long cnp = resultSetChild.getLong(2);
                        String name = resultSetChild.getString(3);
                        int age = resultSetChild.getInt(4);
                        child = new Child(cnp, name, age);
                        child.setId(childId);
                    }
                } catch (SQLException e) {
                    logger.error(e);
                }
                Long challengeId = resultSet.getLong(3);
                Challenge challenge = null;
                try (PreparedStatement preparedStatementChallenge = connection.prepareStatement(
                        "SELECT * FROM challenges " +
                                "WHERE id = ?")) {
                    preparedStatementChallenge.setLong(1, id);
                    ResultSet resultSetChallenge = preparedStatementChallenge.executeQuery();
                    if(resultSet.next())
                    {
                        String name = resultSetChallenge.getString(2);
                        String groupAge = resultSetChallenge.getString(3);
                        int numberOfParticipants = resultSetChallenge.getInt(4);
                        challenge = new Challenge(name, groupAge, numberOfParticipants);
                        challenge.setId(challengeId);
                    }
                } catch (SQLException e) {
                    logger.error(e);
                }
                Enrollment enrollment = new Enrollment(child, challenge);
                enrollment.setId(id);
                return enrollment;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<Enrollment> findAll() {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        List<Enrollment> enrollmentList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM enrollment")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Long id = resultSet.getLong(1);
                Long childId = resultSet.getLong(2);
                Child child = null;
                try (PreparedStatement preparedStatementChild = connection.prepareStatement(
                        "SELECT * FROM children " +
                                "WHERE id = ?")) {
                    preparedStatementChild.setLong(1, childId);
                    ResultSet resultSetChild = preparedStatementChild.executeQuery();
                    if(resultSetChild.next())
                    {
                        Long cnp = resultSetChild.getLong(2);
                        String name = resultSetChild.getString(3);
                        int age = resultSetChild.getInt(4);
                        child = new Child(cnp, name, age);
                        child.setId(childId);
                    }
                } catch (SQLException e) {
                    logger.error(e);
                }
                Long challengeId = resultSet.getLong(3);
                Challenge challenge = null;
                try (PreparedStatement preparedStatementChallenge = connection.prepareStatement(
                        "SELECT * FROM challenges " +
                                "WHERE id = ?")) {
                    preparedStatementChallenge.setLong(1, id);
                    ResultSet resultSetChallenge = preparedStatementChallenge.executeQuery();
                    if(resultSet.next())
                    {
                        String name = resultSetChallenge.getString(2);
                        String groupAge = resultSetChallenge.getString(3);
                        int numberOfParticipants = resultSetChallenge.getInt(4);
                        challenge = new Challenge(name, groupAge, numberOfParticipants);
                        challenge.setId(challengeId);
                    }
                } catch (SQLException e) {
                    logger.error(e);
                }
                Enrollment enrollment = new Enrollment(child, challenge);
                enrollment.setId(id);
                enrollmentList.add(enrollment);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return enrollmentList;
    }

    @Override
    public Collection<Enrollment> getAll() {
        return StreamSupport.stream(findAll().spliterator(), false).toList();
    }
}
