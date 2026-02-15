package com.food.service.impl;

import com.food.model.Ingredients;
import com.food.repository.IngredientsRepository;
import com.food.service.IngredientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientsServiceImpl implements IngredientsService {
    private final IngredientsRepository ingredientsRepository;

    @Override
    public List<Ingredients> findAll() {
        return ingredientsRepository.findAll();
    }

    @Override
    public Ingredients findById(Long id) {
        return ingredientsRepository.findById(id).orElseThrow(() -> new RuntimeException("Ingredient not found"));
    }

    @Override
    public List<Ingredients> findAllByIds(List<Long> ids) {
        return ingredientsRepository.findAllById(ids);
    }
}
