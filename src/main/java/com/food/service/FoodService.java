package com.food.service;

import com.food.model.Foods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface FoodService {
    Foods getFoodById(Long id);

    Page<Foods> findAll(Specification<Foods> spec, PageRequest pageRequest);

    void deleteFoodById(Long id);

    void createFood(Foods foods, Long manufacturerId, List<Long> ingredientIds);

    void updateFood(Long id, String name, Integer calories, Integer amounts, Integer price, Long manufacturerId);

    void assignIngredients(Long foodId, Long ingredientId);

    void unassignIngredient(Long foodId, Long ingredientId);
}
