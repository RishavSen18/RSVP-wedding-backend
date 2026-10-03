package com.example.wedding.service;

import com.example.wedding.dto.ApiResponse;
import com.example.wedding.dto.RsvpRequest;
import com.example.wedding.dto.RsvpResponseDto;
import com.example.wedding.entity.AttendanceStatus;
import com.example.wedding.entity.RsvpResponse;
import com.example.wedding.exception.ResourceNotFoundException;
import com.example.wedding.repository.RsvpResponseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RsvpServiceTest {

    @Mock
    private RsvpResponseRepository rsvpRepository;

    @InjectMocks
    private RsvpService rsvpService;

    @Test
    @DisplayName("saveRsvp should persist entity and return success ApiResponse")
    void saveRsvp_success() {
        RsvpRequest request = new RsvpRequest(
                "Subhashish Mukherjee",
                "+919830000000",
                AttendanceStatus.JOYFULLY_ACCEPT,
                "Blessings to the couple!"
        );

        RsvpResponse savedEntity = new RsvpResponse(
                1L,
                "Subhashish Mukherjee",
                "+919830000000",
                AttendanceStatus.JOYFULLY_ACCEPT,
                "Blessings to the couple!",
                LocalDateTime.now()
        );

        when(rsvpRepository.save(any(RsvpResponse.class))).thenReturn(savedEntity);

        ApiResponse response = rsvpService.saveRsvp(request);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("RSVP submitted successfully", response.getMessage());
        verify(rsvpRepository, times(1)).save(any(RsvpResponse.class));
    }

    @Test
    @DisplayName("getAllResponses should return mapped DTOs ordered by repository")
    void getAllResponses_success() {
        RsvpResponse r1 = new RsvpResponse(1L, "Guest One", "+919800000001", AttendanceStatus.JOYFULLY_ACCEPT, null, LocalDateTime.now());
        RsvpResponse r2 = new RsvpResponse(2L, "Guest Two", "+919800000002", AttendanceStatus.REGRETFULLY_DECLINE, "Best wishes", LocalDateTime.now().minusHours(1));

        when(rsvpRepository.findAllByOrderBySubmittedAtDesc()).thenReturn(List.of(r1, r2));

        List<RsvpResponseDto> dtos = rsvpService.getAllResponses();

        assertEquals(2, dtos.size());
        assertEquals("Guest One", dtos.get(0).getFullName());
        assertEquals(AttendanceStatus.JOYFULLY_ACCEPT, dtos.get(0).getAttendance());
        assertEquals("Guest Two", dtos.get(1).getFullName());
        assertEquals("Best wishes", dtos.get(1).getMessage());
    }

    @Test
    @DisplayName("getResponseById should return DTO when found")
    void getResponseById_found() {
        RsvpResponse r = new RsvpResponse(5L, "Arindam", "+919812345678", AttendanceStatus.JOYFULLY_ACCEPT, "See you there", LocalDateTime.now());
        when(rsvpRepository.findById(5L)).thenReturn(Optional.of(r));

        RsvpResponseDto dto = rsvpService.getResponseById(5L);

        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals("Arindam", dto.getFullName());
    }

    @Test
    @DisplayName("getResponseById should throw ResourceNotFoundException when not found")
    void getResponseById_notFound() {
        when(rsvpRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> rsvpService.getResponseById(99L));
    }

    @Test
    @DisplayName("deleteResponse should delete when exists")
    void deleteResponse_success() {
        when(rsvpRepository.existsById(10L)).thenReturn(true);
        doNothing().when(rsvpRepository).deleteById(10L);

        ApiResponse response = rsvpService.deleteResponse(10L);

        assertTrue(response.isSuccess());
        assertEquals("RSVP response deleted successfully", response.getMessage());
        verify(rsvpRepository, times(1)).deleteById(10L);
    }

    @Test
    @DisplayName("deleteResponse should throw ResourceNotFoundException when not exists")
    void deleteResponse_notFound() {
        when(rsvpRepository.existsById(10L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> rsvpService.deleteResponse(10L));
        verify(rsvpRepository, never()).deleteById(anyLong());
    }
}
