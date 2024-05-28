package repository.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class JdbcUtils {
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPass;
    private Properties jdbcProps;

    private static final Logger logger= LogManager.getLogger();

    public JdbcUtils(Properties props){
        jdbcProps=props;
    }

    @Autowired
    public JdbcUtils(@Value("${jdbc.driver}") String jdbcUrl,
                     @Value("${jdbc.user}") String jdbcUser,
                     @Value("${jdbc.pass}") String jdbcPass) {
        this.jdbcUrl = jdbcUrl;
        this.jdbcUser = jdbcUser;
        this.jdbcPass = jdbcPass;
    }

    private Connection instance=null;

    private Connection getNewConnection(){
        logger.traceEntry();

        String url=jdbcProps.getProperty("jdbc.url");
        String user=jdbcProps.getProperty("jdbc.user");
        String pass=jdbcProps.getProperty("jdbc.pass");
        logger.info("trying to connect to database ... {}",url);
        logger.info("user: {}",user);
        logger.info("pass: {}", pass);
        Connection con=null;
        try {

            if (user!=null && pass!=null)
                con= DriverManager.getConnection(url,user,pass);
            else
                con=DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
        logger.traceEntry();
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(instance);
        return instance;
    }
}
