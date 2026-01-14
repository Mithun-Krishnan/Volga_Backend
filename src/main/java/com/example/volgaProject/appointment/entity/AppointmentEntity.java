package com.example.volgaProject.appointment.entity;

import com.example.volgaProject.appointment.enums.AppointmentStatus;
import com.example.volgaProject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "appointments",
        indexes = {
                @Index(name = "idx_appointment_date", columnList = "appointmentDate"),
                @Index(name = "idx_client_id", columnList = "clientId")
        })
public class AppointmentEntity extends BaseEntity {

    // the id is in base entity
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

    public static AppointmentEntity createRequested(UUID clientId, LocalDate appointmentDate, LocalTime startTime, LocalTime endTime){
        AppointmentEntity appointment=new AppointmentEntity();
        appointment.appointmentDate=appointmentDate;
        appointment.clientId=clientId;
        appointment.endTime=endTime;
        appointment.startTime=startTime;
        appointment.status=AppointmentStatus.REQUESTED;
        return appointment;
    }

    public void confirm(){
        if(this.status!=AppointmentStatus.REQUESTED){
            throw new IllegalStateException("Only REQUESTED appointment can be confirmed");
        }
        this.status=AppointmentStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Completed appointment cannot be cancelled");
        }
        this.status = AppointmentStatus.CANCELLED;
    }

    public void complete() {
        if (this.status != AppointmentStatus.CONFIRMED) {
            throw new IllegalStateException("Only CONFIRMED appointment can be completed");
        }
        this.status = AppointmentStatus.COMPLETED;
    }

    protected AppointmentEntity() {

    }

}
