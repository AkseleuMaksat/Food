package com.food.service;

import com.food.entity.Role;
import com.food.entity.User;
import com.food.repository.RoleRepository;
import com.food.repository.UserRepository;
import com.food.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void loadUserByUsername_UserFound_ReturnsUserDetails() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("password");

        when(userRepository.findByEmail("test@test.com")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("test@test.com");

        assertNotNull(userDetails);
        assertEquals("test@test.com", userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail("test@test.com");
    }

    @Test
    public void loadUserByUsername_UserNotFound_ThrowsException() {
        when(userRepository.findByEmail("unknown@test.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("unknown@test.com"));

        verify(userRepository, times(1)).findByEmail("unknown@test.com");
    }

    @Test
    public void createUser_NewUser_ReturnsSavedUser() {
        User user = new User();
        user.setEmail("new@test.com");
        user.setPassword("rawPassword");

        Role role = new Role();
        role.setRole("ROLE_USER");

        when(userRepository.findByEmail("new@test.com")).thenReturn(null);
        when(roleRepository.findByRole("ROLE_USER")).thenReturn(role);
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("encodedPassword", createdUser.getPassword());
        assertEquals(1, createdUser.getRoles().size());
        assertEquals("ROLE_USER", createdUser.getRoles().getFirst().getRole());

        verify(userRepository, times(1)).findByEmail("new@test.com");
        verify(roleRepository, times(1)).findByRole("ROLE_USER");
        verify(passwordEncoder, times(1)).encode("rawPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void createUser_UserAlreadyExists_ReturnsNull() {
        User user = new User();
        user.setEmail("existing@test.com");

        when(userRepository.findByEmail("existing@test.com")).thenReturn(new User());

        User result = userService.createUser(user);

        assertNull(result);
        verify(userRepository, times(1)).findByEmail("existing@test.com");
        verify(roleRepository, never()).findByRole(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void createUser_RoleNotFound_ReturnsNull() {
        User user = new User();
        user.setEmail("new@test.com");

        when(userRepository.findByEmail("new@test.com")).thenReturn(null);
        when(roleRepository.findByRole("ROLE_USER")).thenReturn(null);

        User result = userService.createUser(user);

        assertNull(result);
        verify(userRepository, times(1)).findByEmail("new@test.com");
        verify(roleRepository, times(1)).findByRole("ROLE_USER");
        verify(userRepository, never()).save(any(User.class));
    }
}
