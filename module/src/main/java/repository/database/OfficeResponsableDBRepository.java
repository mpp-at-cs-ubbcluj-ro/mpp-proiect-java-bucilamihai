package repository.database;

import domain.OfficeResponsable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO office_responsables (username, password) " +
                        "VALUES (?, ?)")) {
            preparedStatement.setString(1, elem.getUsername());
            preparedStatement.setString(2, elem.getPassword());
            int result = preparedStatement.executeUpdate();
            logger.trace("added {} instances ", result);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(OfficeResponsable elem) {
        logger.traceEntry("deleting office responsable {} ", elem);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM office_responsables " +
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
    public void update(OfficeResponsable elem, Long id) {
        logger.traceEntry("updating office responsable {} ", elem);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE office_responsables " +
                        "SET username = ?, password = ? " +
                        "WHERE id = ?")) {
            preparedStatement.setString(1, elem.getUsername());
            preparedStatement.setString(2, elem.getPassword());
            preparedStatement.setLong(3, id);
            int result = preparedStatement.executeUpdate();
            logger.trace("updated {} instances ", result);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
    }

    @Override
    public OfficeResponsable findById(Long id) {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM office_responsables " +
                        "WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                OfficeResponsable officeResponsable = new OfficeResponsable(username, password);
                officeResponsable.setId(id);
                return officeResponsable;
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<OfficeResponsable> findAll() {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        List<OfficeResponsable> officeResponsableList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM office_responsables")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Long id = resultSet.getLong(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                OfficeResponsable officeResponsable = new OfficeResponsable(username, password);
                officeResponsable.setId(id);
                officeResponsableList.add(officeResponsable);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return officeResponsableList;
    }

    @Override
    public Collection<OfficeResponsable> getAll() {
        return StreamSupport.stream(findAll().spliterator(), false).toList();
    }
}
