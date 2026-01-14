package com.example.volgaProject.appointment.repository;

import com.example.volgaProject.appointment.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {

    List<AppointmentEntity> findByAppointmentDate(LocalDate date);


    boolean existsByAppointmentDateAndStartTimeLessThanAndEndTimeGreaterThan(
            LocalDate date,
            LocalTime endTime,
            LocalTime startDate
    );


    List<AppointmentEntity> findByClientId(UUID clientId);

}
