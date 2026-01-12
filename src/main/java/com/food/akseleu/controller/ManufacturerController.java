package com.food.akseleu.controller;

import com.food.akseleu.model.Manufacturer;
import org.springframework.ui.Model;
import com.food.akseleu.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerRepository manufacturerRepository;

    @GetMapping("/add-manufacturer")
    public String addManufacturer(Model model) {
        model.addAttribute("manufacturer", manufacturerRepository.findAll());
        return "add-manufacturer";
    }

    @PostMapping("/add-manufacturer")
    public String addManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
        return "redirect:/";
    }
}
