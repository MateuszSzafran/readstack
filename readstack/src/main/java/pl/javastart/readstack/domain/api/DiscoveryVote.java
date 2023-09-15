package pl.javastart.readstack.domain.api;

public class DiscoveryVote {
    private String username;
    private Integer discoveryId;
    private String type;

    public DiscoveryVote(String username, Integer discoveryId, String type) {
        this.username = username;
        this.discoveryId = discoveryId;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public Integer getDiscoveryId() {
        return discoveryId;
    }

    public String getType() {
        return type;
    }
}
