package com.example.wedding.repository;

import com.example.wedding.entity.RsvpResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for RsvpResponse entity.
 * Provides standard CRUD operations and custom query to retrieve all RSVPs
 * ordered by newest submission first (submittedAt DESC).
 */
@Repository
public interface RsvpResponseRepository extends JpaRepository<RsvpResponse, Long> {

    /**
     * Retrieve all RSVP responses ordered with newest submissions first.
     */
    List<RsvpResponse> findAllByOrderBySubmittedAtDesc();
}
