package com.hotel_management.service;

import com.hotel_management.entity.Property;
import com.hotel_management.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }


    public List<Property> searchHotelByName(String name) {
        return propertyRepository.searchHotels(name);
    }
}
