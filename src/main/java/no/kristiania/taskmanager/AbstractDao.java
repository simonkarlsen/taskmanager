package no.kristiania.taskmanager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> {
    protected DataSource ds;

    public AbstractDao(DataSource ds) {
        this.ds = ds;
    }

    public long insert(T member, String sql) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                insertObject(member, statement);
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }
        }
    }

    protected abstract void insertObject(T member, PreparedStatement statement) throws SQLException;

    public List<T> listAll(String sql) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<T> members = new ArrayList<>();

                    while (rs.next()) {
                        members.add(readObject(rs));
                    }
                    return members;
                }
            }
        }
    }

    protected abstract T readObject(ResultSet rs) throws SQLException;
}
