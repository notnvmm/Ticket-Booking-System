package org.example.ticket_booking.repository;

import org.example.ticket_booking.entity.Seat;
import org.example.ticket_booking.entity.Seat.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    // find all seats for a show that have a specific status
    // Spring auto-generates: SELECT * FROM seats WHERE show_id = ? AND status = ?
    List<Seat> findByShowIdAndStatus(Long showId, SeatStatus status);

    // this one we write ourselves using @Query
    // counts how many seats are still available for a show
    @Query("SELECT COUNT(s) FROM Seat s WHERE s.show.id = :showId AND s.status = 'AVAILABLE'")
    int countAvailableSeats(@Param("showId") Long showId);
}