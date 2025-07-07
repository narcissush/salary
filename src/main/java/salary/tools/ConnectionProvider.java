package salary.tools;

import lombok.Getter;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {
    @Getter
    private static final ConnectionProvider ConnectionProvider =new ConnectionProvider();
    private static final BasicDataSource dataSource=new BasicDataSource();

    private ConnectionProvider(){};

    public Connection getconnection() throws SQLException {
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin123");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(20);
        return dataSource.getConnection();
    }
}
