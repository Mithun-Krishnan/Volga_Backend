package com.example.volgaProject.appointment.service;

import com.example.volgaProject.appointment.dto.TimeSlotDTO;
import com.example.volgaProject.appointment.entity.AppointmentEntity;
import com.example.volgaProject.appointment.repository.AppointmentRepository;
import com.example.volgaProject.exception.BusinessRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor  //for
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;

    @Override
    public UUID requestAppointment(UUID clientId, LocalDate appointmentDate, LocalTime startTime, LocalTime endTime) {
        boolean appointmentExist=appointmentRepository.existsByAppointmentDateAndStartTimeLessThanAndEndTimeGreaterThan(appointmentDate,endTime,startTime);

        if(appointmentExist){
            throw new BusinessRuleException("Time slot is already booked ");
        }

        AppointmentEntity appointment=new AppointmentEntity();

    }

    @Override
    public void confirmAppointment(UUID appointmentId) {

    }

    @Override
    public void cancelAppointment(UUID appointmentId) {

    }

    @Override
    public void completeAppointment(UUID appointmnetId) {

    }

    @Override
    public List<AppointmentEntity> getAppointmentByDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<AppointmentEntity> getAppointmentByClient(UUID clientId) {
        return List.of();
    }

    @Override
    public List<AppointmentEntity> getAppointmentsForToday() {
        return List.of();
    }

    @Override
    public List<TimeSlotDTO> getAvailableSlots(LocalDate date) {
        return List.of();
    }
}
