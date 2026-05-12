Scalable Ticket Booking System

A backend REST API simulating concurrent ticket booking; built to handle simultaneous seat reservations safely without double booking.

Tech Stack

Java 17, Spring Boot, Spring Security, JWT, MySQL, Spring Data JPA, Lombok, Maven

Key Engineering Highlights


Concurrent booking safety — JPA @Version optimistic locking prevents double booking under simultaneous requests without holding database row locks

Atomic transactions — @Transactional ensures seat update + ticket creation succeed or fail together, never leaving the DB in a broken state

Stateless JWT auth — BCrypt password hashing, token-based auth via OncePerRequestFilter, zero server-side sessions

Optimized queries — composite indexes on (show_id, status) for fast seat availability lookups

Clean architecture — strict Controller → Service → Repository separation with global exception handling
