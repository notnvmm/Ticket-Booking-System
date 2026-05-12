package org.example.ticket_booking.controller;

import org.example.ticket_booking.DTO.AuthRequest;
import org.example.ticket_booking.DTO.AuthResponse;
import org.example.ticket_booking.DTO.RegisterRequest;
import org.example.ticket_booking.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController                   //handles HTTP requests
@RequestMapping("/api/auth")      //all endpoints start with api/auth
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")  //api/auth/register

    //@RequestBody means read JSON from request body and convert it into RegisterRequest object
    public ResponseEntity<AuthResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    //api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
