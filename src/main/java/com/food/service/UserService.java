package com.food.service;

import com.food.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User createUser(User user);

    List<User> getAllUsers();

    void changeUserRole(Long userId, String roleName, boolean addRole);
}
