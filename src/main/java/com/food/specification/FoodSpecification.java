package com.food.specification;

import com.food.model.Foods;
import com.food.model.Ingredients;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class FoodSpecification {

    public static Specification<Foods> queryFood(String name, Integer calories, Integer price, Long manufacturerId, Long ingredientId) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // name LIKE
            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            // calories <=
            if (calories != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("calories"), calories));
            }

            // price <=
            if (price != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), price));
            }

            // ManyToOne
            if (manufacturerId != null) {
                predicates.add(cb.equal(root.get("manufacturer").get("id"), manufacturerId));
            }

            //ManyToMany
            if (ingredientId != null) {
                Join<Foods, Ingredients> ingredientJoin = root.join("ingredients", JoinType.INNER);
                predicates.add(cb.equal(ingredientJoin.get("id"), ingredientId));
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}