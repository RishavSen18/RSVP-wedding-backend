package com.example.wedding.controller;

import com.example.wedding.dto.ApiResponse;
import com.example.wedding.dto.RsvpRequest;
import com.example.wedding.dto.RsvpResponseDto;
import com.example.wedding.service.RsvpService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing RSVP submissions.
 * Unauthenticated endpoints for the React wedding invitation website and response dashboard.
 */
@RestController
@RequestMapping("/api/rsvp")
public class RsvpController {

    private final RsvpService rsvpService;

    public RsvpController(RsvpService rsvpService) {
        this.rsvpService = rsvpService;
    }

    /**
     * Submit an RSVP response.
     * Called by the guest RSVP form on the React wedding website.
     * Returns HTTP 201 Created with a simple success message.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> submitRsvp(@Valid @RequestBody RsvpRequest request) {
        ApiResponse response = rsvpService.saveRsvp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all RSVP responses, ordered by newest submissions first.
     * Called by the /wedding-response dashboard page.
     */
    @GetMapping
    public ResponseEntity<List<RsvpResponseDto>> getAllRsvps() {
        List<RsvpResponseDto> responses = rsvpService.getAllResponses();
        return ResponseEntity.ok(responses);
    }

    /**
     * Get a specific RSVP response by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RsvpResponseDto> getRsvpById(@PathVariable Long id) {
        RsvpResponseDto response = rsvpService.getResponseById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete an RSVP response by its ID.
     * Used by the /wedding-response dashboard to remove duplicate or erroneous entries.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRsvp(@PathVariable Long id) {
        ApiResponse response = rsvpService.deleteResponse(id);
        return ResponseEntity.ok(response);
    }
}
