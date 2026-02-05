package com.example.volgaProject.appointment.service;

import com.example.volgaProject.appointment.dto.TimeSlotDTO;
import com.example.volgaProject.appointment.entity.AppointmentEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    UUID requestAppointment(UUID clientId, LocalDate appointmentDate, LocalTime startTime, LocalTime endTime);


    //comand service
    void confirmAppointment(UUID appointmentId);
    void cancelAppointment(UUID appointmentId);
    void completeAppointment(UUID appointmnetId);

    //qury serivce
    List<AppointmentEntity> getAppointmentByDate(LocalDate date);
    List<AppointmentEntity> getAppointmentByClient(UUID clientId);
    List<AppointmentEntity> getAppointmentsForToday();
    List<TimeSlotDTO> getAvailableSlots(LocalDate date);
    void checkStatus(UUID appointmentId);
}
