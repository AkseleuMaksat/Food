package com.food.akseleu.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_foods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Foods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "calories")
    private int calories;

    @Column(name = "amounts")
    private int amounts;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manu_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "t_foods_ingredients",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredients> ingredients;
}
