package com.food.controller;

import com.food.dto.ManufacturerDto;
import com.food.mapper.ManufacturerMapper;
import com.food.model.Manufacturer;
import com.food.repository.ManufacturerRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    @GetMapping("/add-manufacturer")
    public String addManufacturer(Model model) {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        List<ManufacturerDto> manufacturerDtos = manufacturers.stream()
                .map(manufacturerMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("manufacturer", manufacturerDtos);
        return "add-manufacturer";
    }

    @PostMapping("/add-manufacturer")
    public String addManufacturer(ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerDto);
        manufacturerRepository.save(manufacturer);
        return "redirect:/";
    }
}
