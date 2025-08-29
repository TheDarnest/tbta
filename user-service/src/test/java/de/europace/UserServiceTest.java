package de.europace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        userService = new UserService(userRepository);
    }

    @Test
    void registerAndLogin() {
        assertTrue(userService.register("user1", "pass1"));
        String token = userService.login("user1", "pass1");
        assertNotNull(token);
    }

    @Test
    void registerDuplicateUser() {
        assertTrue(userService.register("user2", "pass2"));
        assertFalse(userService.register("user2", "pass2"));
    }

    @Test
    void loginWithWrongPassword() {
        userService.register("user3", "pass3");
        String token = userService.login("user3", "wrongpass");
        assertNull(token);
    }

    @Test
    void verifyToken() {
        userService.register("user4", "pass4");
        String token = userService.login("user4", "pass4");
        String username = userService.verifyToken(token);
        assertEquals("user4", username);
    }

    @Test
    void verifyInvalidToken() {
        String username = userService.verifyToken("invalidtoken");
        assertNull(username);
    }
}

