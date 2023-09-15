package pl.javastart.readstack.domain.category;

import pl.javastart.readstack.config.DataSourceProvider;
import pl.javastart.readstack.domain.common.BaseDao;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDao extends BaseDao {

    public List<Category> getAllCategories() {
        String sql = """
                SELECT
                    id, name, description
                FROM
                    category
                """;
        try (Statement statement = getConnection().createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                categories.add(mapRow(resultSet));
            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Category mapRow(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        return new Category(id, name, description);
    }

    public Optional<Category> findCategoryById(Integer id) {
        String sql = """
                SELECT
                    id, name, description
                FROM
                    category
                WHERE
                    id = ?;
                """;
        try (
             PreparedStatement statement = getConnection().prepareStatement(sql);
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Category category = mapRow(resultSet);
                return Optional.of(category);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
