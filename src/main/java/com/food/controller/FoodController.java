package com.food.controller;

import com.food.dto.FoodDto;
import com.food.dto.IngredientsDto;
import com.food.mapper.FoodMapper;
import com.food.mapper.IngredientsMapper;
import com.food.model.Foods;
import com.food.model.Ingredients;
import com.food.service.FoodService;
import com.food.service.IngredientsService;
import com.food.service.ManufacturerService;
import com.food.specification.FoodSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class FoodController {

    private final ManufacturerService manufacturerService;
    private final IngredientsService ingredientsService;
    private final FoodService foodService;
    private final FoodMapper foodMapper;
    private final IngredientsMapper ingredientsMapper;

    @GetMapping("/")
    public String index(Model model,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "calories", required = false) Integer calories,
            @RequestParam(name = "price", required = false) Integer price,
            @RequestParam(name = "manufacturer", required = false) Long manufacturer,
            @RequestParam(name = "ingredients", required = false) Long ingredients,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "ASC") String sortOrder) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            name = null;
            calories = null;
            price = null;
            manufacturer = null;
            ingredients = null;
            sortBy = "id";
            sortOrder = "ASC";
        }

        Sort sort = Sort.by("DESC".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Specification<Foods> specification = FoodSpecification.queryFood(name, calories, price, manufacturer,
                ingredients);
        Page<Foods> foodsPage = foodService.findAll(specification, pageRequest);
        Page<FoodDto> foodDtoPage = foodsPage.map(foodMapper::toDto);

        model.addAttribute("foods", foodDtoPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", foodsPage.getTotalPages());
        model.addAttribute("size", size);

        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);

        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("ingredients", ingredientsService.findAll());

        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("ingredient", ingredients);
        return "index";
    }

    @GetMapping("/add-food")
    public String addFood(Model model) {
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("ingredients", ingredientsService.findAll());
        return "add-food";
    }

    @PostMapping("/add-food")
    public String addFoods(@Valid FoodDto foodDto,
            @RequestParam(value = "ingredientIds", required = false) List<Long> ingredientIds,
            @RequestParam("manufacturerId") Long manufacturerId) {
        Foods foods = foodMapper.toEntity(foodDto);
        foodService.createFood(foods, manufacturerId, ingredientIds);
        return "redirect:/";
    }

    @GetMapping("update-food/{id}")
    public String editFood(Model model, @PathVariable Long id) {

        Foods foods = foodService.getFoodById(id);
        FoodDto foodDto = foodMapper.toDto(foods);

        List<IngredientsDto> assigned = Optional.ofNullable(foodDto.getIngredients())
                .orElseGet(ArrayList::new);

        List<Ingredients> available = foodService.getAvailableIngredients(id);
        List<IngredientsDto> availableDtos = available.stream()
                .map(ingredientsMapper::toDto)
                .collect(Collectors.toList());

        model.addAttribute("f", foodDto);
        model.addAttribute("assignedIngredients", assigned);
        model.addAttribute("availableIngredients", availableDtos);
        model.addAttribute("manufacturers", manufacturerService.findAll());
        return "update-food";
    }

    @PostMapping("/update/{id}")
    public String updateFoods(@PathVariable Long id,
            @Valid FoodDto foodDto,
            @RequestParam("manufacturer") Long manufacturerId) {
        foodService.updateFood(id, foodDto.getName(), foodDto.getCalories(), foodDto.getAmounts(), foodDto.getPrice(),
                manufacturerId);
        return "redirect:/";
    }

    @PostMapping("/assign-ingredients")
    public String assignIngredients(@RequestParam("food_id") Long foodId,
            @RequestParam("ingredient_id") Long ingredientId) {
        foodService.assignIngredients(foodId, ingredientId);
        return "redirect:/update-food/" + foodId;
    }

    @PostMapping("/unassign-ingredients")
    public String unassignIngredients(@RequestParam("food_id") Long foodId,
            @RequestParam("ingredient_id") Long ingredientId) {
        foodService.unassignIngredient(foodId, ingredientId);
        return "redirect:/update-food/" + foodId;
    }

    @PostMapping("/delete-food/{id}")
    public String deleteFoods(@PathVariable Long id) {
        foodService.deleteFoodById(id);
        return "redirect:/";
    }
}