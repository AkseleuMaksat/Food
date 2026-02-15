package com.food.service;

import com.food.model.Manufacturer;
import com.food.repository.ManufacturerRepository;
import com.food.service.impl.ManufacturerServiceImpl;
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
public class ManufacturerServiceTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private ManufacturerServiceImpl manufacturerService;

    @Test
    public void findAll_ReturnsList() {
        List<Manufacturer> manufacturers = Arrays.asList(new Manufacturer(), new Manufacturer());
        when(manufacturerRepository.findAll()).thenReturn(manufacturers);

        List<Manufacturer> result = manufacturerService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(manufacturerRepository, times(1)).findAll();
    }

    @Test
    public void findById_ManufacturerExists_ReturnsManufacturer() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturer));

        Manufacturer result = manufacturerService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(manufacturerRepository, times(1)).findById(1L);
    }

    @Test
    public void findById_ManufacturerNotFound_ThrowsException() {
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> manufacturerService.findById(1L));

        verify(manufacturerRepository, times(1)).findById(1L);
    }
}
