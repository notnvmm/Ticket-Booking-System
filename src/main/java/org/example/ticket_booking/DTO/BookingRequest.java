package org.example.ticket_booking.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BookingRequest {
    private Long showId;
    private Long seatId;
}
