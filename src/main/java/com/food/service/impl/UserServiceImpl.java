package com.food.service.impl;

import com.food.entity.Role;
import com.food.entity.User;
import com.food.repository.RoleRepository;
import com.food.repository.UserRepository;
import com.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        User checkUser = userRepository.findByEmail(user.getEmail());
        if (checkUser == null) {
            Role role = roleRepository.findByRole("ROLE_USER");
            if (role != null) {
                List<Role> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void changeUserRole(Long userId, String roleName, boolean addRole) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Role role = roleRepository.findByRole(roleName);
            if (role != null) {
                if (addRole) {
                    if (user.getRoles().stream().noneMatch(r -> r.getRole().equals(roleName))) {
                        user.getRoles().add(role);
                    }
                } else {
                    user.getRoles().removeIf(r -> r.getRole().equals(roleName));
                }
                userRepository.save(user);
            }
        }
    }
}
