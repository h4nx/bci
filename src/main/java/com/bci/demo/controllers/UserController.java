package com.bci.demo.controllers;

import com.bci.demo.entity.User;
import com.bci.demo.models.Response;
import com.bci.demo.services.impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Response loginUser(@RequestParam String email, @RequestParam String password) {
        return userService.loginUser(email, password);
    }

    @PostMapping("/register")
    public Response registerUser(@RequestBody @Valid User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/get")
    public UserDetails getLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    class UserAlreadyRegisteredException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public UserAlreadyRegisteredException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    class InvalidLoginException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public InvalidLoginException(String message) {
            super(message);
        }
    }
}