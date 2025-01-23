package com.hotel_management.controller;

import com.hotel_management.entity.AppUser;
import com.hotel_management.service.AppService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AppController {

    private final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/login")
    public String Login(){
        return "that's my boy ";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody AppUser appUser) {
       return appService.createUser(appUser);
    }
}
