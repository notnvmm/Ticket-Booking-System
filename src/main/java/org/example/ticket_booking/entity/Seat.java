package org.example.ticket_booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats", indexes = {
        @Index(name = "idx_show_id", columnList = "show_id"),
        @Index(name = "idx_show_status", columnList = "show_id, status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;              // which show this seat belongs to

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;      // e.g. "A1", "B12"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status = SeatStatus.AVAILABLE;

    @Version                        // THIS handles concurrent booking
    private Long version;

    public enum SeatStatus {
        AVAILABLE, BOOKED
    }
}
