package org.example.ticket_booking.repository;

import org.example.ticket_booking.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    // finds all shows happening after a given time
    List<Show> findByShowTimeAfter(java.time.LocalDateTime time);
}