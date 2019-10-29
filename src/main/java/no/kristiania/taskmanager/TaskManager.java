package no.kristiania.taskmanager;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Properties;

public class TaskManager {

    private PGSimpleDataSource simpleDataSource;
    private BufferedReader input  = new BufferedReader(new InputStreamReader(System.in));
    private MemberDao memberDao;


    public TaskManager() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("task-manager.properties"));

        simpleDataSource = new PGSimpleDataSource();

        simpleDataSource.setUrl(properties.getProperty("dataSource.url"));
        simpleDataSource.setUser(properties.getProperty("dataSource.username"));
        simpleDataSource.setPassword(properties.getProperty("dataSource.password"));

        Flyway.configure().dataSource(simpleDataSource).load().migrate();

        memberDao = new MemberDao(simpleDataSource);

    }

    public static void main(String[] args) throws SQLException, IOException {
        new TaskManager().run();
    }

    private void run() throws IOException, SQLException {
        insertMember();

        System.out.println("Current products " + memberDao.listAll());
    }


    private void insertMember() throws IOException, SQLException {
        Member member = new Member();
        System.out.println("Type the name of the member: ");
        member.setName(input.readLine());
        System.out.println("Type in the email of the member: " );
        member.setEmail(input.readLine());
        System.out.println("Type in the project name the member will work on: " );
        member.setProject(input.readLine());
        memberDao.insert(member);

    }


}
