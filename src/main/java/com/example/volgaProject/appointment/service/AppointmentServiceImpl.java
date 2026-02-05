package com.example.volgaProject.appointment.service;

import com.example.volgaProject.appointment.dto.TimeSlotDTO;
import com.example.volgaProject.appointment.entity.AppointmentEntity;
import com.example.volgaProject.appointment.enums.AppointmentStatus;
import com.example.volgaProject.appointment.repository.AppointmentRepository;
import com.example.volgaProject.exception.BusinessRuleException;
import com.example.volgaProject.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor  //for
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;


    @Override
    @Transactional
    public UUID requestAppointment(UUID clientId, LocalDate appointmentDate, LocalTime startTime, LocalTime endTime) {
        AppointmentEntity appointment=AppointmentEntity.createRequested(clientId,appointmentDate,startTime,endTime);

        boolean overlap=appointmentRepository.
                existsByAppointmentDateAndStartTimeLessThanAndEndTimeGreaterThan(appointmentDate,endTime,startTime);

        if(overlap){
            throw new BusinessRuleException("Time slot already booked");
        }

        AppointmentEntity saved=appointmentRepository.save(appointment);
        return saved.getId();
    }


    @Override
    @Transactional
    public void confirmAppointment(UUID appointmentId) {
        AppointmentEntity appointment=appointmentRepository.findById(appointmentId)
                .orElseThrow(()->new NotFoundException("Appointment not found"));
        appointment.confirm();
        log.info("confirmed");
        appointmentRepository.save(appointment);

    }

    @Override
    @Transactional
    public void cancelAppointment(UUID appointmentId) {
        AppointmentEntity appointment=appointmentRepository.findById(appointmentId)
                .orElseThrow(()->new NotFoundException("Appointment not found"));
        appointment.cancel();
        appointmentRepository.save(appointment);

    }

    @Override
    @Transactional
    public void completeAppointment(UUID appointmentId) {
        AppointmentEntity appointment=appointmentRepository.findById(appointmentId)
                .orElseThrow(()->new NotFoundException("Appointment not found"));
        appointment.complete();
        appointmentRepository.save(appointment);

    }

    @Override
    public List<AppointmentEntity> getAppointmentByDate(LocalDate date) {
        List<AppointmentEntity> appointmentList=appointmentRepository.findByAppointmentDate(date);
        return appointmentList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentEntity> getAppointmentByClient(UUID clientId) {
        List<AppointmentEntity> appointmentList=appointmentRepository.findByClientId(clientId);
       return appointmentList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentEntity> getAppointmentsForToday() {
        List<AppointmentEntity> appointmentList=appointmentRepository.findByAppointmentDate(LocalDate.now());
        return appointmentList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlotDTO> getAvailableSlots(LocalDate date) {
       LocalTime workStart=LocalTime.of(9,0);
       LocalTime workEnd=LocalTime.of(18,0);


       List<AppointmentEntity> appointmentEntityList=appointmentRepository.findByAppointmentDate(date);

       appointmentEntityList.sort(Comparator.comparing(AppointmentEntity::getStartTime));

       LocalTime current =workStart;
       List<TimeSlotDTO> avilableTime=new ArrayList<>();

        for(AppointmentEntity appointment:appointmentEntityList){
            LocalTime apptStart = appointment.getStartTime();
            LocalTime apptEnd   = appointment.getEndTime();

            if (apptStart.isAfter(current)) {
                avilableTime.add(new TimeSlotDTO(current, apptStart));
            }

            if(apptEnd.isAfter(current)){
                current =apptEnd;
            }


        }
        if(current.isBefore(workEnd)){
            avilableTime.add(new TimeSlotDTO(current,workEnd));
        }

        return avilableTime;
    }

    @Override
    public void checkStatus(UUID appointmentId) {
        AppointmentEntity appointment=appointmentRepository.findById(appointmentId).orElseThrow(
                ()->new NotFoundException("no appoinment in this id")
        );

        if(appointment.getStatus()!= AppointmentStatus.COMPLETED){
            throw new BusinessRuleException("Appointmnet is Not Completed");
        }
    }
}
