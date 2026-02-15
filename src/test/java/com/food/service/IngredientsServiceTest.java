package com.food.service;

import com.food.model.Ingredients;
import com.food.repository.IngredientsRepository;
import com.food.service.impl.IngredientsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientsServiceTest {

    @Mock
    private IngredientsRepository ingredientsRepository;

    @InjectMocks
    private IngredientsServiceImpl ingredientsService;

    @Test
    public void findAll_ReturnsList() {
        List<Ingredients> ingredients = Arrays.asList(new Ingredients(), new Ingredients());
        when(ingredientsRepository.findAll()).thenReturn(ingredients);

        List<Ingredients> result = ingredientsService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(ingredientsRepository, times(1)).findAll();
    }

    @Test
    public void findById_IngredientExists_ReturnsIngredient() {
        Ingredients ingredient = new Ingredients();
        ingredient.setId(1L);
        when(ingredientsRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        Ingredients result = ingredientsService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(ingredientsRepository, times(1)).findById(1L);
    }

    @Test
    public void findById_IngredientNotFound_ThrowsException() {
        when(ingredientsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ingredientsService.findById(1L));

        verify(ingredientsRepository, times(1)).findById(1L);
    }

    @Test
    public void findAllByIds_ReturnsList() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Ingredients> ingredients = Arrays.asList(new Ingredients(), new Ingredients());
        when(ingredientsRepository.findAllById(ids)).thenReturn(ingredients);

        List<Ingredients> result = ingredientsService.findAllByIds(ids);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(ingredientsRepository, times(1)).findAllById(ids);
    }
}
