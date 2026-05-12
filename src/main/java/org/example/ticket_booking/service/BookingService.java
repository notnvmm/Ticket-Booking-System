package org.example.ticket_booking.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ticket_booking.entity.Seat;
import org.example.ticket_booking.entity.Show;
import org.example.ticket_booking.entity.Ticket;
import org.example.ticket_booking.entity.User;
import org.example.ticket_booking.repository.SeatRepository;
import org.example.ticket_booking.repository.ShowRepository;
import org.example.ticket_booking.repository.TicketRepository;
import org.example.ticket_booking.repository.UserRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class BookingService {

    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;

    //get all available seats for a show
    public List<Seat> getAvailableSeats(Long showId){
        return seatRepository.findByShowIdAndStatus(showId, Seat.SeatStatus.AVAILABLE);
    }

    //book a seat SAFELY
    @Transactional
    public Ticket bookSeat(Long userId, Long showId, Long seatId){

        //step 1 - find seat in database
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found."));

        //step 2 - is this seat for correct show?
        if (!seat.getShow().getId().equals(showId)){
            throw new RuntimeException("Seat does not belong to this show.");
        }

        //step 3 - is this seat available?
        if (seat.getStatus() != Seat.SeatStatus.AVAILABLE){
            throw new RuntimeException("Sorry! seat not available.");
        }

        //step 4 - mark seat as booked
        seat.setStatus(Seat.SeatStatus.BOOKED);

        //step 5 - save it
        try {
            seatRepository.save(seat);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("Seat was just taken! Please choose another seat.");
        }

        //step 6 - load user and show
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
        Show show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found."));

        //step 7 - create ticket
        Ticket ticket = Ticket.builder()
                .user(user)
                .show(show)
                .seat(seat)
                .bookingReference(
                        "TKT-" + UUID.randomUUID().toString().substring(0,8).toUpperCase()
                ).build();

        //step 8 - save ticket and return it
        return ticketRepository.save(ticket);
    }

    //get all tickets for a user
    public List<Ticket> getUserTickets(Long userId){
        return ticketRepository.findByUserId(userId);
    }

    //cancel booking
    @Transactional
    public void cancelBooking(Long ticketId, Long userId){

        //check if ticket exists
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found."));

        //make sure users can cancel only their OWN ticket
        if (!ticket.getUser().getId().equals(userId)) {
            throw new RuntimeException("Ticket does not belong to this user.");
        }

        //free seat back to available
        Seat seat = ticket.getSeat();
        seat.setStatus(Seat.SeatStatus.AVAILABLE);
        seatRepository.save(seat);

        //delete ticket
        ticketRepository.delete(ticket);
    }
}
