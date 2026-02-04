package com.example.volgaProject.appointment.controller;

import com.example.volgaProject.appointment.dto.TimeSlotDTO;
import com.example.volgaProject.appointment.service.AppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentControler {
    private final AppointmentService appointmentService;


    public AppointmentControler(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // 1. Create appointment
    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENT')")
    public UUID create(
            @RequestParam UUID clientId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime start,
            @RequestParam LocalTime end
    ) {
        return appointmentService.requestAppointment(clientId, date, start, end);
    }

    // 2. Confirm
    @PostMapping("/{id}/confirm")
    public void confirm(@PathVariable UUID id) {
        appointmentService.confirmAppointment(id);
    }

    // 3. Cancel
    @PostMapping("/{id}/cancel")
    public void cancel(@PathVariable UUID id) {
        appointmentService.cancelAppointment(id);
    }

    // 4. Complete
    @PostMapping("/{id}/complete")
    public void complete(@PathVariable UUID id) {
        appointmentService.completeAppointment(id);
    }

    // 5. Get available slots
    @GetMapping("/available-slots")
    public List<TimeSlotDTO> getAvailableSlots(@RequestParam LocalDate date) {
        return appointmentService.getAvailableSlots(date);
    }
}
