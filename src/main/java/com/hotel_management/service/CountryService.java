package com.hotel_management.service;

import com.hotel_management.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    private CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

}
