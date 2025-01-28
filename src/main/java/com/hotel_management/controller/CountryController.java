package com.hotel_management.controller;

import com.hotel_management.entity.AppUser;
import com.hotel_management.service.CountryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/addCountry")
    public AppUser addCountry(
            @AuthenticationPrincipal AppUser user
    ){
        return user;
    }
}
