package com.example.pasir_wojciak_radoslaw.controller;


import com.example.pasir_wojciak_radoslaw.DTO.LoginDto;
import com.example.pasir_wojciak_radoslaw.DTO.UserDto;

import com.example.pasir_wojciak_radoslaw.Service.UserService;
import com.example.pasir_wojciak_radoslaw.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserDto dto)
    {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto)
    {
        try{
            String token = userService.login(dto);
            return ResponseEntity.ok(Map.of("token",token));
        } catch (UsernameNotFoundException | BadCredentialsException ex)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }



}
