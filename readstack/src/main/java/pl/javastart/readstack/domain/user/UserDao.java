package pl.javastart.readstack.domain.user;

import pl.javastart.readstack.domain.common.BaseDao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserDao extends BaseDao {

    public void save(User user) {
        saveUser(user);
        saveUserRole(user);
    }

    private void saveUserRole(User user) {
        String sql = """
                INSERT INTO
                    readstack.user_role(username)
                VALUES
                    (?);
                """;
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql);
        ) {
            statement.setString(1, user.getUsername());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUser(User user) {
        String sql = """
                INSERT INTO 
                    readstack.user(username, email, password, registration_date)
                VALUES
                    (?, ?, ?, ?);
                """;
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setObject(4, user.getRegistrationDate());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findUserByName(String username) {
        String sql = """
                SELECT
                        id, username, email, password, registration_date
                FROM
                        user
                WHERE
                      username = ?;
                """;
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql);
        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRow(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findById(int id) {
        final String sql = """
                SELECT
                    id, username, email, password, registration_date
                FROM
                    user
                WHERE
                    id=?;
                """;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = mapRow(resultSet);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        LocalDateTime registrationDate = resultSet.getObject("registration_date", LocalDateTime.class);
        return new User(id, username, email, password, registrationDate);
    }
}
