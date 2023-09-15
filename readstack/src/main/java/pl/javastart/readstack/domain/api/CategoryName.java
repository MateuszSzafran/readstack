package pl.javastart.readstack.domain.api;

public class CategoryName {
    private Integer id;
    private String name;

    public CategoryName(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
