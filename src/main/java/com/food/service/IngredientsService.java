package com.food.service;

import com.food.model.Ingredients;

import java.util.List;

public interface IngredientsService {
    List<Ingredients> findAll();

    Ingredients findById(Long id);

    List<Ingredients> findAllByIds(List<Long> ids);
}
