package com.bci.demo.services;

import com.bci.demo.entity.User;
import com.bci.demo.models.Response;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public interface IUserService {
    public Response registerUser(User user) throws NoSuchAlgorithmException;

    public Response loginUser(String email, String password) throws NoSuchAlgorithmException;
}
