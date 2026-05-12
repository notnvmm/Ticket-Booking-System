package org.example.ticket_booking.service;

import lombok.RequiredArgsConstructor;
import org.example.ticket_booking.DTO.AuthRequest;
import org.example.ticket_booking.DTO.AuthResponse;
import org.example.ticket_booking.DTO.RegisterRequest;
import org.example.ticket_booking.entity.User;
import org.example.ticket_booking.repository.UserRepository;
import org.example.ticket_booking.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    //called when a new user signs up
    public AuthResponse register (RegisterRequest request){

        //checks if email already exists
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered!");
        }

        //builds new user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        //save to database
        userRepository.save(user);

        //return token so they are logged in immediately
        return new AuthResponse(jwtUtil.generateToken(user.getUsername()), user.getUsername());
    }

    //called when existing user logs in
    public AuthResponse login(AuthRequest request){

        //spring security checks username + password against database
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        //login successful
        return new AuthResponse(jwtUtil.generateToken(request.getUsername()), request.getUsername());
    }
}

