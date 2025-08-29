package de.europace;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<String> getTodos(String username) {
        return todoRepository.getTodos(username);
    }

    public void addTodo(String username, String todo) {
        todoRepository.addTodo(username, todo);
    }

    public void removeTodo(String username, String todo) {
        todoRepository.removeTodo(username, todo);
    }

    public void clearTodos(String username) {
        todoRepository.clearTodos(username);
    }
}
