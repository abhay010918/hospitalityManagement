package com.hotel_management.service;

import com.hotel_management.entity.AppUser;
import com.hotel_management.payload.LoginDto;
import com.hotel_management.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppService {

    private final AppUserRepository appUserRepository;
    private final JWTService jwtService;

    public AppService(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> createUser(AppUser appUser) {
        // Save the AppUser entity directly
        Optional<AppUser> byUsername = appUserRepository.findByUsername(appUser.getUsername());
        if( byUsername.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser> byEmail = appUserRepository.findByEmail(appUser.getEmail());
        if( byEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(4));
        appUser.setPassword(hashpw);
        AppUser save = appUserRepository.save(appUser);
        return new ResponseEntity<>(save,HttpStatus.CREATED);

    }

// login
    public String verifyLogIn(LoginDto loginDto){
        Optional<AppUser> byUsername = appUserRepository.findByUsername(loginDto.getUsername());

        if(byUsername.isPresent()){
            AppUser appUser = byUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword())){
                return jwtService.generateToken(appUser.getUsername());
            }
        }else {
        return null;
        }
        return null;
    }
}
