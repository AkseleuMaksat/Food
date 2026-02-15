package com.food.controller;

import com.food.dto.UserDto;
import com.food.entity.User;
import com.food.mapper.UserMapper;
import com.food.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserDto userDto, @RequestParam String re_password) {
        if (userDto.getPassword().equals(re_password)) {
            User user = userMapper.toEntity(userDto);
            User newUser = userService.createUser(user);
            if (newUser != null) {
                return "redirect:/login?success";
            }
        }
        return "redirect:/register?error";
    }
}
