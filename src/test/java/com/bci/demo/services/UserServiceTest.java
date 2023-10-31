package com.bci.demo.services;

import com.bci.demo.entity.User;
import com.bci.demo.models.Response;
import com.bci.demo.repositories.UserRepository;
import com.bci.demo.security.JwtUtil;
import com.bci.demo.services.impl.UserService;
import com.bci.demo.utils.Constants;
import com.bci.demo.utils.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginUserValidCredentialsTest() {
        User user = new User();
        user.setEmail("testuser@example.com");
        user.setPassword("hashed_password");

        when(userRepository.findByEmail("testuser@example.com")).thenReturn(user);
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);

        Response response = userService.loginUser("testuser@example.com", "password");

        assertTrue(response.getSuccess());
        assertNull(response.getError());
        assertEquals(user, response.getData());
    }

    @Test
    public void loginUserInvalidCredentialsTest() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        Response response = userService.loginUser("nonexistent@example.com", "invalid_password");

        assertFalse(response.getSuccess());
        assertNotNull(response.getError());
        assertEquals(HttpStatus.INVALID_CREDENTIALS, response.getError().getHttpStatus());
        assertEquals(Constants.INVALID_CREDENTIALS, response.getError().getMensaje());
        assertNull(response.getData());
    }

    @Test
    public void registerUserValidUserTest() {
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("password");

        when(userRepository.findByEmail("newuser@example.com")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("hashed_password");
        when(jwtUtil.generateToken("newuser@example.com")).thenReturn("test_token");

        Response response = userService.registerUser(newUser);

        assertTrue(response.getSuccess());
        assertNull(response.getError());
        assertEquals(newUser, response.getData());
    }

    @Test
    public void registerUserDuplicateEmailTest() {
        // Configuración de prueba
        User existingUser = new User();
        existingUser.setEmail("existing@example.com");

        when(userRepository.findByEmail("existing@example.com")).thenReturn(existingUser);

        Response response = userService.registerUser(existingUser);

        assertFalse(response.getSuccess());
        assertNotNull(response.getError());
        assertEquals(HttpStatus.DUPLICATE_EMAIL, response.getError().getHttpStatus());
        assertEquals(Constants.DUPLICATE_EMAIL, response.getError().getMensaje());
        assertNull(response.getData());
    }

}
