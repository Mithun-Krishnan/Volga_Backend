package com.example.volgaProject.project.dto;

import com.example.volgaProject.project.enums.ProjectStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record ProjectWithClientDTO(  UUID projectId,
                                    String projectType,
                                    ProjectStatus status,
                                    BigDecimal estimatedCost,
                                    String clientName,
                                    String clientPhone) {

}
