package com.bci.demo.controllers;

import com.bci.demo.entity.User;
import com.bci.demo.models.Response;
import com.bci.demo.services.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Disabled
@AutoConfigureWebTestClient
public class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    public void loginUserTest() {
        when(userService.loginUser(any(String.class), any(String.class))).thenReturn(new Response());
        Response response = userController.loginUser("test@example.com", "password");
    }

    @Test
    public void registerUserTest() {
        when(userService.registerUser(any(User.class))).thenReturn(new Response());

        User user = new User();
        Response response = userController.registerUser(user);
    }
}
