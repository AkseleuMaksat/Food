package com.food.controller;

import com.food.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @PostMapping("/users/{id}/change-role")
    public String changeRole(@PathVariable Long id,
                             @RequestParam String roleName,
                             @RequestParam boolean addRole,
                             Principal principal) {
        if ("ROLE_ADMIN".equals(roleName) && !principal.getName().equals("admin@gmail.com")) {
            return "redirect:/admin?error=access_denied";
        }
        userService.changeUserRole(id, roleName, addRole);
        return "redirect:/admin";
    }
}
