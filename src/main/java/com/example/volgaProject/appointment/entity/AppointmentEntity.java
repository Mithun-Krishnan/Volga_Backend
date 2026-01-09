package com.example.volgaProject.appointment.entity;

import com.example.volgaProject.appointment.enums.AppointmentStatus;
import com.example.volgaProject.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "appointments",
        indexes = {
                @Index(name = "idx_appointment_date", columnList = "appointmentDate"),
                @Index(name = "idx_client_id", columnList = "clientId")
        })
public class AppointmentEntity extends BaseEntity {

    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Column
    private String remarks;
}
