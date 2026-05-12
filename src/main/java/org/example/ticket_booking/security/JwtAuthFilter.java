package org.example.ticket_booking.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtutil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        //read authorization header
        String authHeader = request.getHeader("Authorization");

        //if no token, skip and pass request to next filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //extract token
        String token = authHeader.substring(7);

        //validate token
        if (jwtutil.isTokenValid(token)){

            //get username
            String username = jwtutil.extractUserName(token);

            //load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //generate authentication object
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            //save it in security context
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        //pass request to next filter
        filterChain.doFilter(request, response);
    }
}
