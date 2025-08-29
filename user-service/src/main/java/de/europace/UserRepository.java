package de.europace;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final Map<String, String> users = new ConcurrentHashMap<>();

    public UserRepository() {
        users.put("admin", "admin"); // Default user
    }

    public boolean exists(String username) {
        return users.containsKey(username);
    }

    public void addUser(String username, String password) {
        users.put(username, password);
    }

    public String getPassword(String username) {
        return users.get(username);
    }
}
