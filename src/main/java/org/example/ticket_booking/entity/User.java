package org.example.ticket_booking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity                          // tells Spring: this class = a database table
@Table(name = "users")          // the table will be named "users" in MySQL
@Getter                          // Lombok: auto-generates getId(), getUsername() etc.
@Setter                          // Lombok: auto-generates setId(), setUsername() etc.
@NoArgsConstructor               // Lombok: creates an empty constructor User()
@AllArgsConstructor              // Lombok: creates constructor with all fields
@Builder                         // Lombok: lets us do User.builder().username("x").build()
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
