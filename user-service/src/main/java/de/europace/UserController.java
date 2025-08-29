package de.europace;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (!userService.register(username, password)) {
            return ResponseEntity.status(409).body("User already exists or invalid input");
        }
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String token = userService.login(username, password);
        if (token == null) {
            return ResponseEntity.status(401).body("Invalid credentials or missing input");
        }
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/token")
    public ResponseEntity<?> verifyToken(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String username = userService.verifyToken(token);
        if (username == null) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        return ResponseEntity.ok(Map.of("username", username));
    }
}
