package pl.javastart.readstack.domain.api;

public class UserRegistration {
    private String username;
    private String email;
    private String password;

    public UserRegistration(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
