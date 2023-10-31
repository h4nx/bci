package com.bci.demo.services.impl;

import com.bci.demo.entity.User;
import com.bci.demo.models.Error;
import com.bci.demo.models.Response;
import com.bci.demo.repositories.UserRepository;
import com.bci.demo.security.JwtUtil;
import com.bci.demo.services.IUserService;
import com.bci.demo.utils.Constants;
import com.bci.demo.utils.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Response loginUser(String email, String password) {
        Response response = new Response();
        Error error = new Error();

        User user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            response.setSuccess(Boolean.FALSE);
            error.setHttpStatus(HttpStatus.INVALID_CREDENTIALS);
            error.setMensaje(Constants.INVALID_CREDENTIALS);
            response.setError(error);
            response.setData(null);

            return response;
        }

        user.setToken(jwtUtil.generateToken(user.getEmail()));
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        response.setSuccess(Boolean.TRUE);
        response.setError(null);
        response.setData(user);

        return response;
    }

    public Response registerUser(User user) {
        Response response = new Response();
        Error error = new Error();

        if (userRepository.findByEmail(user.getEmail()) != null) {
            response.setSuccess(Boolean.FALSE);
            error.setHttpStatus(HttpStatus.DUPLICATE_EMAIL);
            error.setMensaje(Constants.DUPLICATE_EMAIL);
            response.setError(error);
            response.setData(null);

            return response;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken(jwtUtil.generateToken(user.getEmail()));
        user.setActive(true);

        userRepository.save(user);

        response.setSuccess(Boolean.TRUE);
        response.setError(null);
        response.setData(user);

        return response;
    }
}
