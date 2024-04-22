package repository.database;

import domain.Challenge;
import domain.OfficeResponsable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO challenges (name, groupAge, numberOfParticipants) " +
                        "VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, elem.getName());
            preparedStatement.setString(2, elem.getGroupAge());
            preparedStatement.setInt(3, elem.getNumberOfParticipants());
            int result = preparedStatement.executeUpdate();
            logger.trace("added {} instances ", result);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Challenge elem) {
        logger.traceEntry("deleting challenge {} ", elem);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM challenges " +
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
    public void update(Challenge elem, Long id) {
        logger.traceEntry("updating challenge {} ", elem);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE challenges " +
                        "SET name = ?, groupAge = ?, numberOfParticipants = ? " +
                        "WHERE id = ?")) {
            preparedStatement.setString(1, elem.getName());
            preparedStatement.setString(2, elem.getGroupAge());
            preparedStatement.setInt(3, elem.getNumberOfParticipants());
            preparedStatement.setLong(4, id);
            int result = preparedStatement.executeUpdate();
            logger.trace("updated {} instances ", result);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public Challenge findById(Long id) {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM challenges " +
                        "WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                String name = resultSet.getString(2);
                String groupAge = resultSet.getString(3);
                int numberOfParticipants = resultSet.getInt(4);
                Challenge challenge = new Challenge(name, groupAge, numberOfParticipants);
                challenge.setId(id);
                return challenge;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<Challenge> findAll() {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        List<Challenge> challengeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM challenges")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String groupAge = resultSet.getString(3);
                int numberOfParticipants = resultSet.getInt(4);
                Challenge challenge = new Challenge(name, groupAge, numberOfParticipants);
                challenge.setId(id);
                challengeList.add(challenge);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return challengeList;
    }

    @Override
    public Collection<Challenge> getAll() {
        return StreamSupport.stream(findAll().spliterator(), false).toList();
    }

    @Override
    public Challenge getChallengeMatched(int age, String challengeName) {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM challenges " +
                        "WHERE name = ?")) {
            preparedStatement.setString(1, challengeName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Long id = resultSet.getLong(1);
                String groupAge = resultSet.getString(3);
                int numberOfParticipants = resultSet.getInt(4);
                Challenge challenge = new Challenge(challengeName, groupAge, numberOfParticipants);
                challenge.setId(id);
                if(challenge.matchAge(age))
                    return challenge;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return null;
    }
}
