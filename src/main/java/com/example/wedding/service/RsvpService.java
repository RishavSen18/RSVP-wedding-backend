package com.example.wedding.service;

import com.example.wedding.dto.ApiResponse;
import com.example.wedding.dto.RsvpRequest;
import com.example.wedding.dto.RsvpResponseDto;
import com.example.wedding.entity.RsvpResponse;
import com.example.wedding.exception.ResourceNotFoundException;
import com.example.wedding.repository.RsvpResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service encapsulating business logic for processing, saving,
 * retrieving, and deleting wedding RSVP responses.
 */
@Service
@Transactional
public class RsvpService {

    private static final Logger log = LoggerFactory.getLogger(RsvpService.class);

    private final RsvpResponseRepository rsvpRepository;

    public RsvpService(RsvpResponseRepository rsvpRepository) {
        this.rsvpRepository = rsvpRepository;
    }

    /**
     * Process and save a new RSVP submission.
     * Generates submittedAt timestamp on the server.
     * Returns a simple success acknowledgment without exposing saved entity to the guest.
     */
    public ApiResponse saveRsvp(RsvpRequest request) {
        RsvpResponse entity = new RsvpResponse();
        entity.setFirstName(request.getFirstName().trim());
        entity.setLastName(request.getLastName().trim());
        entity.setPhoneNumber(request.getPhoneNumber().trim());
        entity.setAttendance(request.getAttendance());
        
        if (request.getMessage() != null && !request.getMessage().trim().isEmpty()) {
            entity.setMessage(request.getMessage().trim());
        } else {
            entity.setMessage(null);
        }

        // Automatic backend-generated timestamp
        entity.setSubmittedAt(LocalDateTime.now());

        RsvpResponse saved = rsvpRepository.save(entity);
        log.info("Saved RSVP response ID: {} for: {} {}", saved.getId(), saved.getFirstName(), saved.getLastName());

        return ApiResponse.ok("RSVP submitted successfully");
    }

    /**
     * Retrieve all RSVP responses ordered with newest submissions first.
     */
    @Transactional(readOnly = true)
    public List<RsvpResponseDto> getAllResponses() {
        return rsvpRepository.findAllByOrderBySubmittedAtDesc().stream()
                .map(RsvpResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a specific RSVP response by ID.
     * Throws ResourceNotFoundException if ID does not exist.
     */
    @Transactional(readOnly = true)
    public RsvpResponseDto getResponseById(Long id) {
        return rsvpRepository.findById(id)
                .map(RsvpResponseDto::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("RSVP response not found with ID: " + id));
    }

    /**
     * Delete an RSVP response by ID.
     * Throws ResourceNotFoundException if ID does not exist.
     */
    public ApiResponse deleteResponse(Long id) {
        if (!rsvpRepository.existsById(id)) {
            throw new ResourceNotFoundException("RSVP response not found with ID: " + id);
        }

        rsvpRepository.deleteById(id);
        log.info("Deleted RSVP response ID: {}", id);

        return ApiResponse.ok("RSVP response deleted successfully");
    }
}
