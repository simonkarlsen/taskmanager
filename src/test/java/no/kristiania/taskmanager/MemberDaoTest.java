package no.kristiania.taskmanager;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberDaoTest {

    private JdbcDataSource jdbcds;


    //Using BeforeEach instead of writing the same code in every test
    @BeforeEach
    void testDataSource() throws SQLException {
        jdbcds = new JdbcDataSource();
        jdbcds.setUrl("jdbc:h2:mem:dbmembers;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(jdbcds).load().migrate();
    }

    @Test
    void shouldNotBeEmpty() throws SQLException {

        MemberDao dao = new MemberDao(jdbcds);
        Member member = sampleMember();
        dao.insert(member);
        assertThat(dao.listAll()).isNotEmpty();
    }


    @Test
    void shouldListInsertedProduct() throws SQLException {

        MemberDao dao = new MemberDao(jdbcds);
        Member member = sampleMember();
        dao.insert(member);
        assertThat(dao.listAll()).contains(member);
    }

    @Test
    void shouldSaveAllProductFields() throws SQLException {
        MemberDao dao = new MemberDao(jdbcds);
        Member member = sampleMember();
        assertThat(member).hasNoNullFieldsOrProperties();
        long id = dao.insert(member);
        assertThat(dao.retrieve(id))
                .isEqualToComparingFieldByField(member);
    }

    private Member sampleMember() {
        Member member = new Member();
        member.setName(sampleNames());
        member.setEmail(sampleEmails());
        member.setProject(sampleProjects());
        return member;
    }

    private String sampleNames() {
        String[] randomNames = {"Ketil-Raymond", "Per-Eilert", "Tomasutenh", "Berit-Laila", "Fridtjof"
        };
        return randomNames[new Random().nextInt(randomNames.length)];
    }

    private String sampleEmails() {
        String[] randomEmails = {"mailkongen@hotmail.com", "persen@gmail.com", "hansen@live.no",
                "ok@outlook.no", "epost@mail.com"
        };
        return randomEmails[new Random().nextInt(randomEmails.length)];
    }

    private String sampleProjects() {
        String[] randomProjects = {"Project A", "Project B", "Project C",
                "Project D", "Project E"
        };
        return randomProjects[new Random().nextInt(randomProjects.length)];
    }


}
