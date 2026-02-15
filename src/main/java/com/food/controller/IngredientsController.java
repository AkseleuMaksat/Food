package com.food.controller;

import com.food.dto.IngredientsDto;
import com.food.mapper.IngredientsMapper;
import com.food.model.Ingredients;
import com.food.repository.IngredientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class IngredientsController {
    private final IngredientsRepository ingredientsRepository;
    private final IngredientsMapper ingredientsMapper;

    @GetMapping("/add-ingredients")
    public String getAddIngredients() {
        return "add-ingredients";
    }

    @PostMapping("/add-ingredients")
    public String postAddIngredients(@RequestParam(name = "name") String name) {
        IngredientsDto ingredientsDto = IngredientsDto.builder().name(name).build();
        Ingredients ingredients = ingredientsMapper.toEntity(ingredientsDto);
        ingredientsRepository.save(ingredients);
        return "redirect:/";
    }
}
