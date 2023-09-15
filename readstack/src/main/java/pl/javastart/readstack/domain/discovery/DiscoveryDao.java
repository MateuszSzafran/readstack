package pl.javastart.readstack.domain.discovery;

import pl.javastart.readstack.config.DataSourceProvider;
import pl.javastart.readstack.domain.category.Category;
import pl.javastart.readstack.domain.common.BaseDao;

import javax.naming.ConfigurationException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscoveryDao extends BaseDao {

    public List<Discovery> findAll() {
        final String query = """
                Select
                    id, title, url, description, date_added, category_id, user_id
                FROM
                    discovery;
                """;
        try (
                Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Discovery> allDiscoveries = new ArrayList<>();
            while (resultSet.next()) {
                Discovery discovery = mapRow(resultSet);
                allDiscoveries.add(discovery);
            }
            return allDiscoveries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Discovery> findByCategory(int categoryId) {
        List<Discovery> discoveries = new ArrayList<>();
        String sql = """
                SELECT
                    id, title, url, description, date_added, category_id, user_id
                FROM
                    discovery
                WHERE
                    category_id = ?;
                """;
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql);
        ) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                discoveries.add(mapRow(resultSet));
            }
            return discoveries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Discovery discovery) {
        String sql = """
                INSERT INTO 
                    discovery (title, url, description, date_added, category_id, user_id)
                VALUES
                    (?,?,?,?,?,?);
                """;
        try (
                PreparedStatement statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ) {
            statement.setString(1, discovery.getTitle());
            statement.setString(2, discovery.getUrl());
            statement.setString(3, discovery.getDescription());
            statement.setObject(4, discovery.getDateAdded());
            statement.setInt(5, discovery.getCategoryId());
            statement.setInt(6, discovery.getUserId());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()){
                discovery.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Discovery mapRow(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String url = resultSet.getString("url");
        String description = resultSet.getString("description");
        LocalDateTime dateAdded = resultSet.getTimestamp("date_added").toLocalDateTime();
        Integer categoryId = resultSet.getInt("category_id");
        Integer userId = resultSet.getInt("user_id");
        return new Discovery(id, title, url, description, dateAdded, categoryId, userId);
    }
}
