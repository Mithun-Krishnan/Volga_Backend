package com.example.volgaProject.project.entity;

import com.example.volgaProject.common.entity.BaseEntity;
import com.example.volgaProject.project.enums.ProjectStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(indexes = {
        @Index(name = "idx_project_client_id",columnList = "clientId"),
        @Index(name = "idx_project_status",columnList = "status")
})
public class ProjectEntity extends BaseEntity {
    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private String type; // Kitchen, Bedroom, Full house, etc.

    @Column(nullable = false)
    private BigDecimal estimatedCost;

    @Column
    private BigDecimal actualCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;
}
