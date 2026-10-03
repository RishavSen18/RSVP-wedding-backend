package com.example.wedding.controller;

import com.example.wedding.entity.AttendanceStatus;
import com.example.wedding.entity.RsvpResponse;
import com.example.wedding.repository.RsvpResponseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RsvpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RsvpResponseRepository rsvpRepository;

    @BeforeEach
    void setUp() {
        rsvpRepository.deleteAll();
    }

    @Nested
    @DisplayName("POST /api/rsvp tests")
    class PostRsvpTests {

        @Test
        @DisplayName("Should successfully create RSVP with valid data")
        void submitRsvp_validRequest() throws Exception {
            String payload = """
                {
                    "fullName": "Subhashish Mukherjee",
                    "phoneNumber": "+919830000000",
                    "attendance": "JOYFULLY_ACCEPT",
                    "message": "Wishing you both a lifetime of happiness!"
                }
                """;

            mockMvc.perform(post("/api/rsvp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.message").value("RSVP submitted successfully"));
        }

        @Test
        @DisplayName("Should successfully create RSVP when optional message is null or omitted")
        void submitRsvp_optionalMessageOmitted() throws Exception {
            String payload = """
                {
                    "fullName": "Priya Banerjee",
                    "phoneNumber": "+919831112233",
                    "attendance": "REGRETFULLY_DECLINE"
                }
                """;

            mockMvc.perform(post("/api/rsvp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.message").value("RSVP submitted successfully"));
        }

        @Test
        @DisplayName("Should fail when fullName is missing or blank")
        void submitRsvp_missingFullName() throws Exception {
            String payload = """
                {
                    "fullName": "",
                    "phoneNumber": "+919830000000",
                    "attendance": "JOYFULLY_ACCEPT"
                }
                """;

            mockMvc.perform(post("/api/rsvp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message", containsString("Full name is required")));
        }

        @Test
        @DisplayName("Should fail when phoneNumber is missing or blank")
        void submitRsvp_missingPhoneNumber() throws Exception {
            String payload = """
                {
                    "fullName": "Amitabh Sen",
                    "phoneNumber": "   ",
                    "attendance": "JOYFULLY_ACCEPT"
                }
                """;

            mockMvc.perform(post("/api/rsvp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Should fail when attendance is missing")
        void submitRsvp_missingAttendance() throws Exception {
            String payload = """
                {
                    "fullName": "Ananya Roy",
                    "phoneNumber": "+919830000000"
                }
                """;

            mockMvc.perform(post("/api/rsvp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Should fail when attendance is invalid string")
        void submitRsvp_invalidAttendance() throws Exception {
            String payload = """
                {
                    "fullName": "Rohit Das",
                    "phoneNumber": "+919830000000",
                    "attendance": "MAYBE_LATER"
                }
                """;

            mockMvc.perform(post("/api/rsvp")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message", containsString("Invalid attendance value")));
        }
    }

    @Nested
    @DisplayName("GET /api/rsvp tests")
    class GetAllRsvpsTests {

        @Test
        @DisplayName("Should return empty list when database has no responses")
        void getAllRsvps_emptyDatabase() throws Exception {
            mockMvc.perform(get("/api/rsvp"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        @DisplayName("Should return multiple responses ordered by newest-first (submittedAt DESC)")
        void getAllRsvps_multipleResponses_orderedNewestFirst() throws Exception {
            // Older submission
            RsvpResponse first = new RsvpResponse();
            first.setFullName("Older Guest");
            first.setPhoneNumber("+919811111111");
            first.setAttendance(AttendanceStatus.REGRETFULLY_DECLINE);
            first.setMessage("Warm wishes");
            first.setSubmittedAt(LocalDateTime.of(2026, 10, 1, 10, 0));
            rsvpRepository.save(first);

            // Newer submission
            RsvpResponse second = new RsvpResponse();
            second.setFullName("Newer Guest");
            second.setPhoneNumber("+919822222222");
            second.setAttendance(AttendanceStatus.JOYFULLY_ACCEPT);
            second.setMessage("Cannot wait to celebrate!");
            second.setSubmittedAt(LocalDateTime.of(2026, 10, 2, 12, 0));
            rsvpRepository.save(second);

            mockMvc.perform(get("/api/rsvp"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].fullName").value("Newer Guest"))
                    .andExpect(jsonPath("$[0].attendance").value("JOYFULLY_ACCEPT"))
                    .andExpect(jsonPath("$[1].fullName").value("Older Guest"))
                    .andExpect(jsonPath("$[1].attendance").value("REGRETFULLY_DECLINE"));
        }
    }

    @Nested
    @DisplayName("GET /api/rsvp/{id} tests")
    class GetRsvpByIdTests {

        @Test
        @DisplayName("Should return response when ID exists")
        void getRsvpById_existingResponse() throws Exception {
            RsvpResponse saved = new RsvpResponse();
            saved.setFullName("Sourav Ganguly");
            saved.setPhoneNumber("+919830099999");
            saved.setAttendance(AttendanceStatus.JOYFULLY_ACCEPT);
            saved.setMessage("All the best!");
            saved.setSubmittedAt(LocalDateTime.now());
            saved = rsvpRepository.save(saved);

            mockMvc.perform(get("/api/rsvp/{id}", saved.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(saved.getId()))
                    .andExpect(jsonPath("$.fullName").value("Sourav Ganguly"))
                    .andExpect(jsonPath("$.phoneNumber").value("+919830099999"))
                    .andExpect(jsonPath("$.attendance").value("JOYFULLY_ACCEPT"))
                    .andExpect(jsonPath("$.message").value("All the best!"))
                    .andExpect(jsonPath("$.submittedAt").exists());
        }

        @Test
        @DisplayName("Should return 404 when ID does not exist")
        void getRsvpById_nonexistentResponse() throws Exception {
            mockMvc.perform(get("/api/rsvp/{id}", 9999L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message", containsString("RSVP response not found with ID: 9999")));
        }
    }

    @Nested
    @DisplayName("DELETE /api/rsvp/{id} tests")
    class DeleteRsvpTests {

        @Test
        @DisplayName("Should successfully delete existing response")
        void deleteRsvp_existingResponse() throws Exception {
            RsvpResponse saved = new RsvpResponse();
            saved.setFullName("Debashis Chatterjee");
            saved.setPhoneNumber("+919830055555");
            saved.setAttendance(AttendanceStatus.JOYFULLY_ACCEPT);
            saved.setSubmittedAt(LocalDateTime.now());
            saved = rsvpRepository.save(saved);

            mockMvc.perform(delete("/api/rsvp/{id}", saved.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.message").value("RSVP response deleted successfully"));

            // Verify deleted from database
            mockMvc.perform(get("/api/rsvp/{id}", saved.getId()))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 404 when deleting nonexistent ID")
        void deleteRsvp_nonexistentResponse() throws Exception {
            mockMvc.perform(delete("/api/rsvp/{id}", 8888L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message", containsString("RSVP response not found with ID: 8888")));
        }
    }
}
