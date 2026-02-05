package com.example.volgaProject.project.controller;

import com.example.volgaProject.project.entity.ProjectEntity;
import com.example.volgaProject.project.enums.ProjectStatus;
import com.example.volgaProject.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test/projects")
public class ProjectControler {

    private final ProjectService projectService;

    // 1️⃣ Create project from appointment
    @PostMapping("/from-appointment")
    public UUID createProject(
            @RequestParam UUID appointmentId,
            @RequestParam BigDecimal estimatedCost,
            @RequestParam String type
    ) {
        return projectService.createProjectFromAppointment(appointmentId, estimatedCost, type);
    }

    // 2️⃣ Start project
    @PostMapping("/{projectId}/start")
    public void startProject(@PathVariable UUID projectId) {
        projectService.startProject(projectId);
    }

    // 3️⃣ Update estimate
    @PostMapping("/{projectId}/estimate")
    public void updateEstimate(
            @PathVariable UUID projectId,
            @RequestParam BigDecimal newEstimate
    ) {
        projectService.updateEstimate(projectId, newEstimate);
    }

    // 4️⃣ Complete project
    @PostMapping("/{projectId}/complete")
    public void completeProject(
            @PathVariable UUID projectId,
            @RequestParam BigDecimal actualCost
    ) {
        projectService.completeProject(projectId, actualCost);
    }

    // 5️⃣ Cancel project
    @PostMapping("/{projectId}/cancel")
    public void cancelProject(@PathVariable UUID projectId) {
        projectService.cancelProject(projectId);
    }

    // 6️⃣ View by status
    @GetMapping
    public List<ProjectEntity> getByStatus(@RequestParam ProjectStatus status) {
        return projectService.getProjectByStatus(status);
    }
}
