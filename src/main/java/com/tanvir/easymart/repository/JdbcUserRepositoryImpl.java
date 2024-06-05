package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.jdbc.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class JdbcUserRepositoryImpl implements UserRepository{
    private static Logger LOGGER = LoggerFactory.getLogger(JdbcUserRepositoryImpl.class);
    private DataSource dataSource = ConnectionPool.getInstance().getDataSource();

    private final static String SAVE_USER = "INSERT INTO user " +
            "(username," +
            "password," +
            "version," +
            "date_created," +
            "date_last_updated," +
            "email," +
            "first_name," +
            "last_name) " +
            "VALUES (?,?,?,?,?)";

    private final static String SELECT_BY_USERNAME = "SELECT " +
            " id, " +
            " username, " +
            " password, " +
            " version, " +
            " date_created, " +
            " date_last_updated, " +
            " email, " +
            " first_name, " +
            " last_name FROM user WHERE username = ?";

    @Override
    public void save(User user) {
        try (var connection = dataSource.getConnection();
             var prepareStatement = connection.prepareStatement(SAVE_USER)) {
            prepareStatement.setString(1, user.getUsername());
            prepareStatement.setString(2, user.getPassword());
            prepareStatement.setLong(3, user.getVersion());
            prepareStatement.setTimestamp(4, Timestamp.valueOf(user.getDateCreated()));
            prepareStatement.setTimestamp(5, Timestamp.valueOf(user.getDateLastUpdated()));
            prepareStatement.setString(6, user.getEmail());
            prepareStatement.setString(7, user.getFirstName());
            prepareStatement.setString(8, user.getLastName());
            prepareStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Unable to save user", e);
            throw new RuntimeException("Unable to save user", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (var connection = dataSource.getConnection();
             var prepareStatement = connection.prepareStatement(SELECT_BY_USERNAME)) {
            prepareStatement.setString(1, username);
            var resultSet = prepareStatement.executeQuery();
            List<User> users = extractUsers(resultSet);
            if (users.size() > 0) {
                return Optional.of(users.get(0));

            }
        } catch (Exception e) {
            LOGGER.error("Unable to fetch user", e);
            throw new RuntimeException("Unable to fetch user", e);
        }
        return Optional.empty();
    }

    private List<User> extractUsers(ResultSet resultSet) throws SQLException {
        List<User> users = List.of();
        while (resultSet.next()) {
            var user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setVersion(resultSet.getLong("version"));
            user.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
            user.setDateLastUpdated(resultSet.getTimestamp("date_last_updated").toLocalDateTime());
            user.setEmail(resultSet.getString("email"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            users.add(user);
        }
        return users;
    }
}
