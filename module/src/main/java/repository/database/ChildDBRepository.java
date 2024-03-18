package repository.database;

import domain.Child;
import domain.OfficeResponsable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO child (cnp, name, age) " +
                        "VALUES (?, ?, ?)")) {
            preparedStatement.setLong(1, elem.getCnp());
            preparedStatement.setString(2, elem.getName());
            preparedStatement.setInt(3, elem.getAge());
            int result = preparedStatement.executeUpdate();
            logger.trace("added {} instances ", result);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Child elem) {
        logger.traceEntry("deleting child {} ", elem);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM child " +
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
    public void update(Child elem, Long id) {
        logger.traceEntry("updating child {} ", elem);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE child " +
                        "SET cnp = ?, name = ?, age = ? " +
                        "WHERE id = ?")) {
            preparedStatement.setLong(1, elem.getCnp());
            preparedStatement.setString(2, elem.getName());
            preparedStatement.setInt(3, elem.getAge());
            preparedStatement.setLong(4, id);
            int result = preparedStatement.executeUpdate();
            logger.trace("updated {} instances ", result);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public Child findById(Long id) {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM children " +
                        "WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                Long cnp = resultSet.getLong(2);
                String name = resultSet.getString(3);
                int age = resultSet.getInt(4);
                Child child = new Child(cnp, name, age);
                child.setId(id);
                return child;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<Child> findAll() {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        List<Child> childList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM children")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Long id = resultSet.getLong(1);
                Long cnp = resultSet.getLong(2);
                String name = resultSet.getString(3);
                int age = resultSet.getInt(4);
                Child child = new Child(cnp, name, age);
                child.setId(id);
                childList.add(child);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return childList;
    }

    @Override
    public Collection<Child> getAll() {
        return StreamSupport.stream(findAll().spliterator(), false).toList();
    }
}
