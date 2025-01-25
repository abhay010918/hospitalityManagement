package com.hotel_management.controller;
import com.hotel_management.entity.AppUser;
import com.hotel_management.payload.LoginDto;
import com.hotel_management.payload.TokenDto;
import com.hotel_management.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody AppUser appUser) {
       return appService.createUser(appUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogIn(
            @RequestBody LoginDto loginDto){
        String token = appService.verifyLogIn(loginDto);
        if (token != null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }else {
            return new ResponseEntity<>("invalid username/password", HttpStatus.FORBIDDEN);
        }
    }
}
