package pl.javastart.readstack.domain.api;

import org.apache.commons.codec.digest.DigestUtils;
import pl.javastart.readstack.domain.user.User;
import pl.javastart.readstack.domain.user.UserDao;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class UserService {
    private UserDao userDao = new UserDao();

    public void register(UserRegistration user){
        User userToSave = UserMapper.map(user);
        try {
            hashPasswordWithSha256(userToSave);
            userDao.save(userToSave);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void hashPasswordWithSha256(User user) throws NoSuchAlgorithmException {
        String sha256Password = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword(sha256Password);
    }

    private static class UserMapper{
        static User map(UserRegistration user){
            String email = user.getEmail();
            String username = user.getUsername();
            String password = user.getPassword();
            return new User(username, email, password, LocalDateTime.now());
        }
    }
}
