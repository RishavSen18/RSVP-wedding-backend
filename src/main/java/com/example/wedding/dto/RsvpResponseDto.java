package com.example.wedding.dto;

import com.example.wedding.entity.AttendanceStatus;
import com.example.wedding.entity.RsvpResponse;

import java.time.LocalDateTime;

/**
 * Data transfer object returned when querying RSVP responses.
 * Used by the /wedding-response dashboard to list and inspect submissions.
 */
public class RsvpResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AttendanceStatus attendance;
    private String message;
    private LocalDateTime submittedAt;

    public RsvpResponseDto() {
    }

    public RsvpResponseDto(Long id, String firstName, String lastName, String phoneNumber, AttendanceStatus attendance, String message, LocalDateTime submittedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.attendance = attendance;
        this.message = message;
        this.submittedAt = submittedAt;
    }

    public static RsvpResponseDto fromEntity(RsvpResponse entity) {
        if (entity == null) {
            return null;
        }
        return new RsvpResponseDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhoneNumber(),
                entity.getAttendance(),
                entity.getMessage(),
                entity.getSubmittedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AttendanceStatus getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceStatus attendance) {
        this.attendance = attendance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
