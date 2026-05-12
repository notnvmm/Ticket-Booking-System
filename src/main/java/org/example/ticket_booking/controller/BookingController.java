package org.example.ticket_booking.controller;

import org.example.ticket_booking.DTO.BookingRequest;
import org.example.ticket_booking.entity.Seat;
import org.example.ticket_booking.entity.Ticket;
import org.example.ticket_booking.entity.User;
import org.example.ticket_booking.repository.UserRepository;
import org.example.ticket_booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class BookingController {
    private final BookingService bookingService;
    private final UserRepository userRepository;

    //see all available seats for a show
    @GetMapping("/shows/{showId}/seats")
    public ResponseEntity<List<Seat>> getAvailableSeats (@PathVariable Long showId) {
        return ResponseEntity.ok(bookingService.getAvailableSeats(showId));
    }

    //book a seat
    @PostMapping("/book")
    public ResponseEntity<Ticket> bookSeat (
            @RequestBody BookingRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = bookingService.bookSeat(
                user.getId(),
                request.getShowId(),
                request.getSeatId()
        );

        return ResponseEntity.ok(ticket);
    }

    //get all tickets for logged-in user
    @GetMapping("/my-tickets")
    public ResponseEntity<List<Ticket>> getMyTickets (@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found."));

        return ResponseEntity.ok(bookingService.getUserTickets(user.getId()));
    }

    //cancel booking
    @DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<String> cancelTicket (
            @PathVariable Long ticketId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found."));

        bookingService.cancelBooking(ticketId, user.getId());
        return ResponseEntity.ok("Ticket cancelled successfully.");
    }
}
