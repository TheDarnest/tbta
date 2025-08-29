package de.europace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TodoServiceTest {
    private TodoService todoService;
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository = new TodoRepository();
        todoService = new TodoService(todoRepository);
    }

    @Test
    void addAndGetTodos() {
        todoService.addTodo("user1", "Test Todo");
        List<String> todos = todoService.getTodos("user1");
        assertEquals(1, todos.size());
        assertEquals("Test Todo", todos.get(0));
    }

    @Test
    void removeTodo() {
        todoService.addTodo("user1", "Test Todo");
        todoService.removeTodo("user1", "Test Todo");
        List<String> todos = todoService.getTodos("user1");
        assertTrue(todos.isEmpty());
    }

    @Test
    void clearTodos() {
        todoService.addTodo("user1", "Todo1");
        todoService.addTodo("user1", "Todo2");
        todoService.clearTodos("user1");
        List<String> todos = todoService.getTodos("user1");
        assertTrue(todos.isEmpty());
    }
}

