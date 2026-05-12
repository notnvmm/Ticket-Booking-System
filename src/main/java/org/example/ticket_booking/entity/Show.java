package org.example.ticket_booking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;            // e.g. "Arijit Singh Live Concert"

    @Column(nullable = false)
    private String venue;           // e.g. "Jawaharlal Nehru Stadium, Delhi"

    @Column(name = "show_time", nullable = false)
    private LocalDateTime showTime; // date and time of the show

    @Column(name = "total_seats")
    private int totalSeats;         // e.g. 500
}
