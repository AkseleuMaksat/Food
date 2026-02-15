package com.food.service;

import com.food.model.Manufacturer;
import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> findAll();

    Manufacturer findById(Long id);
}
