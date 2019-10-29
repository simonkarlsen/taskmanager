package no.kristiania.taskmanager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberDao extends AbstractDao<Member> {

    public MemberDao(DataSource ds) {
        super(ds);
    }


    public long insert(Member member) throws SQLException {
        return insert(member, "insert into members (name, email, project) values (?, ?, ?)");
    }

    @Override
    protected void insertObject(Member member, PreparedStatement statement) throws SQLException {
        statement.setString(1, member.getName());
        statement.setString(2, member.getEmail());
        statement.setString(3, member.getProject());
    }

    @Override
    protected Member readObject(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setProject(rs.getString("project"));
        return member;
    }

    public List<Member> listAll() throws SQLException {
        return listAll("select * from members");
    }

    public Member retrieve(long id) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from members where id = ?")) {
                statement.setLong(1, id);
                try (ResultSet rs = statement.executeQuery()){
                    if (rs.next()) {
                        return readObject(rs);
                    }
                    else {
                        return null;
                    }
                }
            }
        }
    }
}
