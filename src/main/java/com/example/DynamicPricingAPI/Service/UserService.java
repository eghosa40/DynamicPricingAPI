package com.example.DynamicPricingAPI.Service;

import com.example.DynamicPricingAPI.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserId(Long Id);
    List<User> getAllUsers();
    User updateUser(Long Id, User user);
    void deleteUser(User user);
    Optional<User> getUserByEmail(String email);
}
