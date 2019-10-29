package no.kristiania.taskmanager;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

public class DatabaseTest {
    public static JdbcDataSource testDataSource() {
        JdbcDataSource jdbcds = new JdbcDataSource();
        jdbcds.setUrl("jdbc:h2:mem:dbmembers;DB_CLOSE_DELAY=-1;"); //ADD: DB_CLOSE_DELAY=-1 to keep the DB open

        Flyway.configure().dataSource(jdbcds).load().migrate();
        return jdbcds;
    }
}
