package com.example.wedding.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA Entity mapping to the PostgreSQL 'rsvp_responses' table.
 * Contains: firstName, lastName, phoneNumber, attendance, optional message,
 * and the backend-generated submittedAt timestamp.
 * 
 * Strict requirement: NO email, NO guest_count, NO number_of_guests.
 */
@Entity
@Table(name = "rsvp_responses")
public class RsvpResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "phone_number", nullable = false, length = 30)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendance", nullable = false, length = 30)
    private AttendanceStatus attendance;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    public RsvpResponse() {
    }

    public RsvpResponse(String firstName, String lastName, String phoneNumber, AttendanceStatus attendance, String message) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.attendance = attendance;
        this.message = message;
        this.submittedAt = LocalDateTime.now();
    }

    public RsvpResponse(Long id, String firstName, String lastName, String phoneNumber, AttendanceStatus attendance, String message, LocalDateTime submittedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.attendance = attendance;
        this.message = message;
        this.submittedAt = submittedAt;
    }

    @PrePersist
    protected void onCreate() {
        if (this.submittedAt == null) {
            this.submittedAt = LocalDateTime.now();
        }
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

    @Override
    public String toString() {
        return "RsvpResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", attendance=" + attendance +
                ", message='" + (message != null ? message.substring(0, Math.min(message.length(), 20)) + "..." : "null") + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
