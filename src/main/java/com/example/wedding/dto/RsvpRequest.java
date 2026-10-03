package com.example.wedding.dto;

import com.example.wedding.entity.AttendanceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Incoming request DTO for guest RSVP submission.
 * Captures firstName and lastName separately.
 * 
 * Strict constraints:
 * - firstName is required and non-blank.
 * - lastName is required and non-blank.
 * - phoneNumber is stored as a String (accepting +91, leading zeros, dashes).
 * - attendance must be either JOYFULLY_ACCEPT or REGRETFULLY_DECLINE.
 * - message is optional with reasonable max length.
 * - Must NOT contain id or submittedAt (backend controls them).
 * - Must NOT contain email or guest count.
 */
public class RsvpRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Size(max = 30, message = "Phone number must not exceed 30 characters")
    @Pattern(
        regexp = "^[+]?[0-9\\s\\-().]{7,25}$",
        message = "Phone number must be a valid format (e.g. +919830000000, 09830000000, etc.)"
    )
    private String phoneNumber;

    @NotNull(message = "Attendance status is required (JOYFULLY_ACCEPT or REGRETFULLY_DECLINE)")
    private AttendanceStatus attendance;

    @Size(max = 1000, message = "Blessings message must not exceed 1000 characters")
    private String message;

    public RsvpRequest() {
    }

    public RsvpRequest(String firstName, String lastName, String phoneNumber, AttendanceStatus attendance, String message) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.attendance = attendance;
        this.message = message;
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
}
