package org.example.ticket_booking.service;

import org.example.ticket_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // Spring Security calls this automatically when someone tries to log in
    // It says "give me the user with this username"
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // go to DB, find the user
        return userRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())  // already hashed
                        .roles("USER")
                        .build())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username)
                );
    }
}