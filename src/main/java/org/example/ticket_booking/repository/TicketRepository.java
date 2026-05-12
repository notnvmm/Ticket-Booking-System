package org.example.ticket_booking.repository;

import org.example.ticket_booking.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // get all tickets booked by a specific user
    // SELECT * FROM tickets WHERE user_id = ?
    List<Ticket> findByUserId(Long userId);

    // check if a seat already has a ticket
    // SELECT COUNT(*) FROM tickets WHERE seat_id = ?
    boolean existsBySeatId(Long seatId);
}