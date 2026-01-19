package com.food.controller;

import com.food.model.Foods;
import com.food.model.Ingredients;
import com.food.model.Manufacturer;
import com.food.repository.FoodRepository;
import com.food.repository.IngredientsRepository;
import com.food.repository.ManufacturerRepository;
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

    private final FoodRepository repository;
    private final IngredientsRepository ingredientsRepository;
    private final ManufacturerRepository manufacturerRepository;

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
        Page<Foods> foodsPage = repository.findAll(specification, pageRequest);

        model.addAttribute("foods", foodsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", foodsPage.getTotalPages());
        model.addAttribute("size", size);

        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);

        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("ingredients", ingredientsRepository.findAll());

        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("ingredient", ingredients);
        return "index";
    }

    @GetMapping("/add-food")
    public String addFood(Model model) {
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("ingredients", ingredientsRepository.findAll());
        return "add-food";
    }

    @PostMapping("/add-food")
    public String addFoods(Foods foods,
                           @RequestParam(value = "ingredientIds", required = false) List<Long> ingredientIds,
                           @RequestParam("manufacturer") Long manufacturerId
    ) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId).orElseThrow(() -> new RuntimeException("Manufacturer not found"));
        foods.setManufacturer(manufacturer);

        if (ingredientIds != null && !ingredientIds.isEmpty()) {
            List<Ingredients> ingredients = ingredientsRepository.findAllById(ingredientIds);
            foods.setIngredients(ingredients);
        }
        repository.save(foods);
        return "redirect:/";
    }

    @GetMapping("update-food/{id}")
    public String editFood(Model model, @PathVariable Long id) {

        Optional<Foods> food = repository.findById(id);

        if (food.isPresent()) {
            Foods foods = food.get();
            List<Ingredients> assigned = foods.getIngredients();
            List<Ingredients> all = ingredientsRepository.findAll();

            if (assigned == null) {
                assigned = new ArrayList<>();
            }

            List<Ingredients> available = new ArrayList<>(all);
            available.removeAll(assigned);

            model.addAttribute("f", foods);
            model.addAttribute("assignedIngredients", assigned);
            model.addAttribute("availableIngredients", available);
            model.addAttribute("manufacturers", manufacturerRepository.findAll());

            return "update-food";
        }
        return "error";
    }

    @PostMapping("/update/{id}")
    public String updateFoods(@PathVariable Long id,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "calories") Integer calories,
                              @RequestParam(name = "amounts") Integer amounts,
                              @RequestParam(name = "price") Integer price,
                              @RequestParam("manufacturer") Long manufacturerId
    ) {
        Foods food = repository.findById(id).orElseThrow();
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId).orElseThrow();
        food.setName(name);
        food.setCalories(calories);
        food.setAmounts(amounts);
        food.setPrice(price);
        food.setManufacturer(manufacturer);
        repository.save(food);
        return "redirect:/";
    }

    @PostMapping("/assign-ingredients")
    public String assignIngredients(@RequestParam("food_id") Long foodId,
                                    @RequestParam("ingredient_id") Long ingredientId) {

        Foods food = repository.findById(foodId).orElseThrow();
        Ingredients ingredient = ingredientsRepository.findById(ingredientId).orElseThrow();

        List<Ingredients> ingredientsList = food.getIngredients();

        if (ingredientsList == null) {
            ingredientsList = new ArrayList<>();
        }

        if (!ingredientsList.contains(ingredient)) {
            ingredientsList.add(ingredient);
        }

        food.setIngredients(ingredientsList);
        repository.save(food);

        return "redirect:/update-food/" + foodId;
    }

    @PostMapping("/unassign-ingredients")
    public String unassignIngredients(@RequestParam("food_id") Long foodId,
                                      @RequestParam("ingredient_id") Long ingredientId) {

        Foods food = repository.findById(foodId).orElse(null);
        if (food != null) {
            Ingredients ingredient = ingredientsRepository.findById(ingredientId).orElse(null);
            if (ingredient != null) {
                List<Ingredients> ingredientsList = food.getIngredients();
                if (ingredientsList == null) {
                    ingredientsList = new ArrayList<>();
                }
                ingredientsList.remove(ingredient);
                food.setIngredients(ingredientsList);
                repository.save(food);
            }
            return "redirect:/update-food/" + foodId;
        }
        return "redirect:/404";
    }

    @PostMapping("/delete-food/{id}")
    public String deleteFoods(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}
