package com.hotel_management.service;

import com.hotel_management.entity.Images;
import com.hotel_management.entity.Property;
import com.hotel_management.repository.ImagesRepository;
import com.hotel_management.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final ImagesRepository imagesRepository;

    public PropertyService(PropertyRepository propertyRepository,
                           ImagesRepository imagesRepository) {
        this.propertyRepository = propertyRepository;
        this.imagesRepository = imagesRepository;
    }


    public List<Property> searchHotelByName(String name) {
        return propertyRepository.searchHotels(name);
    }

    public void saveImageUrl(long propertyId, String imageUrl) {
        // Fetch the property entity
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // Create a new Images entity
        Images image = new Images();
        image.setUrl(imageUrl);
        image.setProperty(property); // Associate the image with the property

        // Save the image entity
        imagesRepository.save(image);
    }

    public Property findById(long propertyId) {
        return propertyRepository.findById(propertyId).get();
    }
}
