package com.example.volgaProject.project.service;

import com.example.volgaProject.project.entity.ProjectEntity;
import com.example.volgaProject.project.enums.ProjectStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProjectService {
    UUID createProjectFromAppointment(UUID appointmentId, BigDecimal estimateCost,String type);
    void startProject(UUID projectId);
    void completeProject(UUID projectId,BigDecimal actualAmount);
    void cancelProject(UUID projectId);
//    void onHoldProject(UUID projectId);
    void updateEstimate(UUID projectId,BigDecimal estimateAmt);


//    List<ProjectEntity> getProjectByClient(UUID clientId);   no longer use the clinet id in entity

    List<ProjectEntity> getProjectByStatus(ProjectStatus status);



}
