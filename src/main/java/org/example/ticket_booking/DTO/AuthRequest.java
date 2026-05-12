package org.example.ticket_booking.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AuthRequest {
    private String username;
    private String password;
}
