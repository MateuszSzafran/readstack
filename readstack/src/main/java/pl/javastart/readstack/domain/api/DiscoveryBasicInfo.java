package pl.javastart.readstack.domain.api;

import java.time.LocalDateTime;

public class DiscoveryBasicInfo {
    private Integer id;
    private String title;
    private String url;
    private String description;
    private LocalDateTime dateAdded;
    private int voteCount;
    private String author;

    public DiscoveryBasicInfo(Integer id, String title, String url, String description, LocalDateTime dateAdded, int voteCount, String author) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.description = description;
        this.dateAdded = dateAdded;
        this.voteCount = voteCount;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getAuthor(){
        return author;
    }
}
