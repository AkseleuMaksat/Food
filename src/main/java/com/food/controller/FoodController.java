package com.food.controller;

import com.food.model.Foods;
import com.food.model.Ingredients;
import com.food.service.FoodService;
import com.food.service.IngredientsService;
import com.food.service.ManufacturerService;
import com.food.specification.FoodSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class FoodController {

    //    private final FoodRepository repository;
//    private final IngredientsRepository ingredientsRepository;
//    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerService manufacturerService;
    private final IngredientsService ingredientsService;
    private final FoodService foodService;

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
        Sort sort = Sort.by("DESC".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Specification<Foods> specification = FoodSpecification.queryFood(name, calories, price, manufacturer, ingredients);
        Page<Foods> foodsPage = foodService.findAll(specification, pageRequest);

        model.addAttribute("foods", foodsPage);
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
    public String addFoods(Foods foods,
                           @RequestParam(value = "ingredientIds", required = false) List<Long> ingredientIds,
                           @RequestParam("manufacturer") Long manufacturerId
    ) {
        foodService.createFood(foods, manufacturerId, ingredientIds);
        return "redirect:/";
    }

    @GetMapping("update-food/{id}")
    public String editFood(Model model, @PathVariable Long id) {

        Foods foods = foodService.getFoodById(id);

        List<Ingredients> assigned =
                Optional.ofNullable(foods.getIngredients())
                        .orElseGet(ArrayList::new);

        List<Ingredients> available =
                new ArrayList<>(ingredientsService.findAll());
        available.removeAll(assigned);

        model.addAttribute("f", foods);
        model.addAttribute("assignedIngredients", assigned);
        model.addAttribute("availableIngredients", available);
        model.addAttribute("manufacturers", manufacturerService.findAll());
        return "update-food";
    }

    @PostMapping("/update/{id}")
    public String updateFoods(@PathVariable Long id,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "calories") Integer calories,
                              @RequestParam(name = "amounts") Integer amounts,
                              @RequestParam(name = "price") Integer price,
                              @RequestParam("manufacturer") Long manufacturerId
    ) {
        foodService.updateFood(id, name, calories, amounts, price, manufacturerId);
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
