package de.europace;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TodoRepository {
    private final Map<String, List<String>> todos = new ConcurrentHashMap<>();

    public List<String> getTodos(String username) {
        return todos.getOrDefault(username, new ArrayList<>());
    }

    public void addTodo(String username, String todo) {
        todos.computeIfAbsent(username, k -> new ArrayList<>()).add(todo);
    }

    public void removeTodo(String username, String todo) {
        List<String> userTodos = todos.get(username);
        if (userTodos != null) {
            userTodos.remove(todo);
        }
    }

    public void clearTodos(String username) {
        todos.remove(username);
    }
}
