package com.hotel_management.controller;

import com.hotel_management.entity.Property;
import com.hotel_management.service.PropertyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/byName")
    public List<Property> searchHotel(
            @RequestParam String name
    ){
        return propertyService.searchHotelByName(name);
    }
}
