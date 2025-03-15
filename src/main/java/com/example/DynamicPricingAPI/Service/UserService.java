package com.example.DynamicPricingAPI.Service;

import com.example.DynamicPricingAPI.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    String loginUser(String email, String password);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> getUserByEmail(String email);
}

