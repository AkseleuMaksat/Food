package com.food.service;

import com.food.model.Foods;
import com.food.model.Ingredients;
import com.food.model.Manufacturer;
import com.food.repository.FoodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private IngredientsService ingredientsService;

    @Mock
    private ManufacturerService manufacturerService;

    @InjectMocks
    private FoodService foodService;

    @Test
    public void getFoodById_FoodExists_ReturnsFood() {
        Foods food = new Foods();
        food.setId(1L);
        when(foodRepository.findById(1L)).thenReturn(Optional.of(food));

        Foods result = foodService.getFoodById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(foodRepository, times(1)).findById(1L);
    }

    @Test
    public void getFoodById_FoodNotFound_ThrowsException() {
        when(foodRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            foodService.getFoodById(1L);
        });

        assertEquals("Еда не найдена", exception.getMessage());
        verify(foodRepository, times(1)).findById(1L);
    }

    @Test
    public void findAll_ReturnsPage() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Foods> page = new PageImpl<>(Collections.emptyList());
        when(foodRepository.findAll(nullable(Specification.class), eq(pageRequest))).thenReturn(page);

        Page<Foods> result = foodService.findAll(null, pageRequest);

        assertNotNull(result);
        verify(foodRepository, times(1)).findAll(nullable(Specification.class), eq(pageRequest));
    }

    @Test
    public void createFood_ValidInput_SavesFood() {
        Foods food = new Foods();
        Long manufacturerId = 1L;
        List<Long> ingredientIds = Arrays.asList(1L, 2L);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);

        List<Ingredients> ingredients = Arrays.asList(new Ingredients(), new Ingredients());

        when(manufacturerService.findById(manufacturerId)).thenReturn(manufacturer);
        when(ingredientsService.findAllByIds(ingredientIds)).thenReturn(ingredients);
        when(foodRepository.save(any(Foods.class))).thenReturn(food);

        foodService.createFood(food, manufacturerId, ingredientIds);

        verify(manufacturerService, times(1)).findById(manufacturerId);
        verify(ingredientsService, times(1)).findAllByIds(ingredientIds);
        verify(foodRepository, times(1)).save(food);
    }

    @Test
    public void updateFood_ValidInput_UpdatesFood() {
        Foods food = new Foods();
        food.setId(1L);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(2L);

        when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
        when(manufacturerService.findById(2L)).thenReturn(manufacturer);

        foodService.updateFood(1L, "New Name", 100, 10, 50, 2L);

        assertEquals("New Name", food.getName());
        assertEquals(100, food.getCalories());
        assertEquals(10, food.getAmounts());
        assertEquals(50, food.getPrice());
        assertEquals(manufacturer, food.getManufacturer());
    }

    @Test
    public void assignIngredients_IngredientNotPresent_AddsIngredient() {
        Foods food = new Foods();
        food.setId(1L);
        food.setIngredients(new ArrayList<>());

        Ingredients ingredient = new Ingredients();
        ingredient.setId(2L);

        when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
        when(ingredientsService.findById(2L)).thenReturn(ingredient);

        foodService.assignIngredients(1L, 2L);

        assertTrue(food.getIngredients().contains(ingredient));
    }

    @Test
    public void unassignIngredient_IngredientPresent_RemovesIngredient() {
        Foods food = new Foods();
        food.setId(1L);
        Ingredients ingredient = new Ingredients();
        ingredient.setId(2L);
        List<Ingredients> ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        food.setIngredients(ingredients);

        when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
        when(ingredientsService.findById(2L)).thenReturn(ingredient);

        foodService.unassignIngredient(1L, 2L);

        assertFalse(food.getIngredients().contains(ingredient));
    }

    @Test
    public void deleteFoodById_DeletesFood() {
        foodService.deleteFoodById(1L);
        verify(foodRepository, times(1)).deleteById(1L);
    }
}
