package com.example.volgaProject.project.service;

import com.example.volgaProject.appointment.entity.AppointmentEntity;
import com.example.volgaProject.appointment.enums.AppointmentStatus;
import com.example.volgaProject.appointment.service.AppointmentService;
import com.example.volgaProject.exception.BusinessRuleException;
import com.example.volgaProject.exception.NotFoundException;
import com.example.volgaProject.project.entity.ProjectEntity;
import com.example.volgaProject.project.enums.ProjectStatus;
import com.example.volgaProject.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService{

    final ProjectRepository projectRepository;
    final AppointmentService appointmentService;

    @Override
    public UUID createProjectFromAppointment(UUID appointmentId, BigDecimal estimateCost, String type) {

        appointmentService.checkStatus(appointmentId);

        if(projectRepository.existsByAppointmentId(appointmentId)){
            throw new BusinessRuleException("Project already exist for this application");
        }

        ProjectEntity projectEntity=
                ProjectEntity.createProjectFromAppointment(appointmentId,estimateCost,type);

        ProjectEntity saved=projectRepository.save(projectEntity);

        return saved.getId();
    }

    @Override
    public void startProject(UUID projectId) {
        ProjectEntity projectEntity=projectRepository.findById(projectId).orElseThrow(
                ()->new NotFoundException("no project in this is")
        );
        projectEntity.startProject();
    }

    @Override
    public void completeProject(UUID projectId, BigDecimal actualAmount) {
        ProjectEntity projectEntity=loadProject(projectId);
        projectEntity.completeProject(actualAmount);
    }

    @Override
    public void cancelProject(UUID projectId) {
        ProjectEntity projectEntity=loadProject(projectId);
        projectEntity.cancelProject();

    }

    @Override
    public void updateEstimate(UUID projectId, BigDecimal estimateAmt) {
        ProjectEntity projectEntity=loadProject(projectId);
        projectEntity.updateEstimate(estimateAmt);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectEntity> getProjectByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }


    private ProjectEntity loadProject(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }
}
