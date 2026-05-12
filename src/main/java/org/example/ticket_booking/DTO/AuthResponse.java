package org.example.ticket_booking.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AuthResponse {
    private String token;
    private String username;
}
