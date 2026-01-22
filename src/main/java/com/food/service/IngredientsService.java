package com.food.service;

import com.food.model.Ingredients;
import com.food.repository.IngredientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientsService {
    private final IngredientsRepository ingredientsRepository;

    public List<Ingredients> findAll() {
        return ingredientsRepository.findAll();
    }
    public Ingredients findById(Long id) {
        return ingredientsRepository.findById(id).orElseThrow(() -> new RuntimeException("Ingredient not found"));
    }

    public List<Ingredients> findAllByIds(List<Long> ids) {
        return ingredientsRepository.findAllById(ids);
    }
}
