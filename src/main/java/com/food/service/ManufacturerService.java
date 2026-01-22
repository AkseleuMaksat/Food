package com.food.service;

import com.food.model.Manufacturer;
import com.food.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }
    public Manufacturer findById(Long id) {
        return manufacturerRepository.findById(id).orElseThrow(() -> new RuntimeException("Manufacturer not found"));
    }
}
