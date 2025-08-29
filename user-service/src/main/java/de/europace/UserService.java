package de.europace;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String username, String password) {
        if (username == null || password == null || userRepository.exists(username)) {
            return false;
        }
        userRepository.addUser(username, password);
        return true;
    }

    public String login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        String storedPassword = userRepository.getPassword(username);
        if (!password.equals(storedPassword)) {
            return null;
        }
        return Base64.getEncoder().encodeToString(("user:" + username).getBytes());
    }

    public String verifyToken(String token) {
        if (token == null) {
            return null;
        }
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            if (decoded.startsWith("user:")) {
                String username = decoded.substring(5);
                if (userRepository.exists(username)) {
                    return username;
                }
            }
        } catch (Exception ignored) {}
        return null;
    }
}
