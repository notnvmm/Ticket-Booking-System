package org.example.ticket_booking.controller;

import org.example.ticket_booking.entity.Show;
import org.example.ticket_booking.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor

public class ShowController {
    private final ShowRepository showRepository;

    //returns all shows in database
    @GetMapping
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(showRepository.findAll());
    }

    //return one specific show by id
    @GetMapping("/{id}")
    public ResponseEntity<Show> getShow (@PathVariable Long id){
        Show show = showRepository.findById(id).orElseThrow(() -> new RuntimeException("Show not found with id:" + id));
        return ResponseEntity.ok(show);
    }

    //creates new show
    @PostMapping
    public ResponseEntity<Show> createShow(@RequestBody Show show) {
        return ResponseEntity.ok(showRepository.save(show));
    }
}
