package de.europace;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class UserController {
    private final Map<String, String> users = new ConcurrentHashMap<>();

    public UserController() {
        users.put("admin", "admin"); // Default user
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Missing username or password");
        }
        if (users.containsKey(username)) {
            return ResponseEntity.status(409).body("User already exists");
        }
        users.put(username, password);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Missing username or password");
        }
        if (!password.equals(users.get(username))) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = Base64.getEncoder().encodeToString(("user:" + username).getBytes());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/token")
    public ResponseEntity<?> verifyToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        if (token == null) {
            return ResponseEntity.badRequest().body("Missing token");
        }
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            if (!decoded.startsWith("user:")) {
                return ResponseEntity.status(401).body("Invalid token");
            }
            String username = decoded.substring(5);
            if (!users.containsKey(username)) {
                return ResponseEntity.status(401).body("Invalid token");
            }
            return ResponseEntity.ok(Map.of("username", username));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}
