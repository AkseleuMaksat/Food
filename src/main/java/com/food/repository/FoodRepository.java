package com.food.repository;

import com.food.model.Foods;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Foods, Long> {
    @Query("SELECT f FROM Foods f where lower(f.name) LIKE %:name% " +
            "and :cal is not null or f.calories<=:cal")
    List<Foods> findAllAkseleu(@Param(value = "name") String name, @Param(value = "cal")  Integer cal);

    List<Foods> findAll(Specification<Foods> specification);
}
