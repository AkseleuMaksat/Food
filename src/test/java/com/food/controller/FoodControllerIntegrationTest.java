package com.food.controller;

import com.food.BaseIntegrationTest;
import com.food.model.Manufacturer;
import com.food.repository.ManufacturerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FoodControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldAddFoodSuccessfully() throws Exception {
        // Given
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Test Manufacturer");
        manufacturer.setCode("TM");
        manufacturer = manufacturerRepository.save(manufacturer);

        // When & Then
        mockMvc.perform(post("/add-food")
                        .param("name", "Test Food")
                        .param("calories", "100")
                        .param("amounts", "10")
                        .param("price", "50")
                        .param("manufacturerId", manufacturer.getId().toString())
                        .with(csrf())) // CSRF token is required for POST requests
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldFailValidationWhenNameIsEmpty() throws Exception {
        // Given
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Test Manufacturer");
        manufacturer.setCode("TM");
        manufacturer = manufacturerRepository.save(manufacturer);

        // When & Then (Validation Error Handling - depends on GlobalExceptionHandler
        // implementation)
        // Since GlobalExceptionHandler returns "error" view, we expect that.
        mockMvc.perform(post("/add-food")
                        .param("name", "") // Invalid: Empty name
                        .param("calories", "100")
                        .param("amounts", "10")
                        .param("price", "50")
                        .param("manufacturerId", manufacturer.getId().toString())
                        .with(csrf()))
                .andExpect(status().isOk()) // 200 OK because it returns an error view, not 400 Bad Request directly
                .andExpect(view().name("error"));
    }
}
