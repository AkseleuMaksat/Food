package com.food.akseleu.controller;

import com.food.akseleu.model.Ingredients;
import com.food.akseleu.repository.IngredientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class IngredientsController {
    private final IngredientsRepository ingredientsRepository;

    @GetMapping("/add-ingredients")
    public String getAddIngredients() {
        return "add-ingredients";
    }

    @PostMapping("/add-ingredients")
    public String postAddIngredients(@RequestParam(name = "name") String name) {
        Ingredients ingredients = new Ingredients();
        ingredients.setName(name);
        ingredientsRepository.save(ingredients);
        return "redirect:/";
    }
}
