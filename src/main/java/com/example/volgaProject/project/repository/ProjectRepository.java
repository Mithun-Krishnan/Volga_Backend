package com.example.volgaProject.project.repository;

import com.example.volgaProject.project.dto.ProjectWithClientDTO;
import com.example.volgaProject.project.entity.ProjectEntity;
import com.example.volgaProject.project.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {

    boolean existsByAppointmentId(UUID appointmentId);

    List<ProjectEntity>findByStatus(ProjectStatus status);

}
