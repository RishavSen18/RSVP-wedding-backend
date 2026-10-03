package com.example.wedding.entity;

/**
 * Attendance status options for the wedding invitation RSVP.
 * Exactly two valid states matching the frontend Bengali and English options:
 * - JOYFULLY_ACCEPT ("JOYFULLY ACCEPT / আনন্দসহকারে উপস্থিত থাকব")
 * - REGRETFULLY_DECLINE ("REGRETFULLY DECLINE / সম্ভবত অপারগতা")
 */
public enum AttendanceStatus {
    JOYFULLY_ACCEPT,
    REGRETFULLY_DECLINE
}
