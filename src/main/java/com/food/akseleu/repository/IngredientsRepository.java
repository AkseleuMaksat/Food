package com.food.akseleu.repository;

import com.food.akseleu.model.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {
}
