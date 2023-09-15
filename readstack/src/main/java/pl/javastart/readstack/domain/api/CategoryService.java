package pl.javastart.readstack.domain.api;

import pl.javastart.readstack.domain.category.Category;
import pl.javastart.readstack.domain.category.CategoryDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryService {
    private final CategoryDao categoryDao = new CategoryDao();

    public List<CategoryName> findAllCategoryNames() {
        return categoryDao.getAllCategories()
                .stream()
                .map(CategoryNameMapper::mapCategoryFromDao)
                .collect(Collectors.toList());
    }

    private static class CategoryNameMapper {
        static CategoryName mapCategoryFromDao(Category category) {
            Integer id = category.getId();
            String name = category.getName();
            return new CategoryName(id, name);
        }
    }

    public Optional<CategoryFullInfo> findCategoryById(Integer id) {
        return categoryDao.findCategoryById(id)
                .map(CategoryFullInfoMapper::map);
    }

    private static class CategoryFullInfoMapper {
        static CategoryFullInfo map(Category category) {
            return new CategoryFullInfo(
                    category.getId(),
                    category.getName(),
                    category.getDescription()
            );
        }
    }
}
