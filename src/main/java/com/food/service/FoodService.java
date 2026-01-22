package com.food.service;

import com.food.model.Foods;
import com.food.model.Ingredients;
import com.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository repository;
    private final IngredientsService ingredientService;
    private final ManufacturerService manufacturerService;

    public Foods getFoodById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Еда не найдена"));
    }

    public Page<Foods> findAll(Specification<Foods> spec, PageRequest pageRequest) {
        return repository.findAll(spec, pageRequest);
    }

    public void deleteFoodById(Long id) {
        repository.deleteById(id);
    }

    public void createFood(Foods foods, Long manufacturerId, List<Long> ingredientIds) {
        foods.setManufacturer(manufacturerService.findById(manufacturerId));
        if (ingredientIds != null && !ingredientIds.isEmpty()) {
            foods.setIngredients(ingredientService.findAllByIds(ingredientIds));
        }
        repository.save(foods);
    }

    @Transactional
    public void updateFood(Long id, String name, Integer calories, Integer amounts, Integer price, Long manufacturerId) {
        Foods food = getFoodById(id);
        food.setName(name);
        food.setCalories(calories);
        food.setAmounts(amounts);
        food.setPrice(price);
        food.setManufacturer(manufacturerService.findById(manufacturerId));
    }

    @Transactional
    public void assignIngredients(Long foodId, Long ingredientId) {
        Foods food = getFoodById(foodId);
        Ingredients ingredient = ingredientService.findById(ingredientId);

        List<Ingredients> ingredients =
                Optional.ofNullable(food.getIngredients())
                        .orElseGet(ArrayList::new);

        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
            food.setIngredients(ingredients);
        }
    }

    @Transactional
    public void unassignIngredient(Long foodId, Long ingredientId) {
        Foods food = getFoodById(foodId);
        Ingredients ingredient = ingredientService.findById(ingredientId);

        if (food.getIngredients() != null) {
            food.getIngredients().remove(ingredient);
        }
    }
}
