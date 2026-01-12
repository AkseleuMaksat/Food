package com.food.akseleu.repository;

import com.food.akseleu.model.Foods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Foods, Long> {
    List<Foods> findByPriceGreaterThan(Integer price);
    List<Foods> findByNameContainingIgnoreCase(String price);
}
