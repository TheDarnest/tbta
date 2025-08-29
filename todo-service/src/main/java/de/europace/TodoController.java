package de.europace;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class TodoController {
    private final Map<String, List<String>> todos = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${USER_SERVICE_URL:http://localhost:8081}")
    private String userServiceUrl;

    private String getUsernameFromToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = Map.of("token", token);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(userServiceUrl + "/token", request, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("username");
            }
        } catch (Exception ignored) {}
        return null;
    }

    @PostMapping("/todos")
    public ResponseEntity<?> addTodo(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                     @RequestBody Map<String, String> body) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid token");
        }
        String token = authHeader.substring(7);
        String username = getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        String todo = body.get("todo");
        if (todo == null) {
            return ResponseEntity.badRequest().body("Missing todo");
        }
        todos.computeIfAbsent(username, k -> new ArrayList<>()).add(todo);
        return ResponseEntity.ok("Todo added");
    }

    @GetMapping("/todos")
    public ResponseEntity<?> getTodos(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid token");
        }
        String token = authHeader.substring(7);
        String username = getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        List<String> userTodos = todos.getOrDefault(username, Collections.emptyList());
        return ResponseEntity.ok(Map.of("todos", userTodos));
    }
}
