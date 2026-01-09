package com.example.volgaProject.appointment.dto;

import java.time.LocalTime;

public record TimeSlotDTO (
        LocalTime startTime,
        LocalTime endTime
){ }
