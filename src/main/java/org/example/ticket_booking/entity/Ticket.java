package org.example.ticket_booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_booking_ref", columnList = "booking_reference")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;              // who booked this ticket

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;              // which seat was booked

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;              // which show it's for

    @Column(name = "booking_reference", unique = true)
    private String bookingReference; // e.g. "TKT-AB12CD34"

    @Column(name = "booked_at")
    private LocalDateTime bookedAt;

    @PrePersist
    public void prePersist() {
        this.bookedAt = LocalDateTime.now();
    }
}
