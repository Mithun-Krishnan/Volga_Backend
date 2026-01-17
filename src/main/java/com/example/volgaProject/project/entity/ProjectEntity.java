package com.example.volgaProject.project.entity;

import com.example.volgaProject.common.entity.BaseEntity;
import com.example.volgaProject.exception.BusinessRuleException;
import com.example.volgaProject.project.enums.ProjectStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(indexes = {
//        @Index(name = "idx_project_client_id",columnList = "clientId"),
        @Index(name = "idx_project_status",columnList = "status")
})
public class ProjectEntity extends BaseEntity {
//    @Column(nullable = true)
//    private UUID clientId;

    @Column(nullable = false)
    private UUID appointmentId;

    @Column(nullable = false)
    private String type; // Kitchen, Bedroom, Full house, etc.

    @Column(nullable = false)
    private BigDecimal estimatedCost;

    @Column
    private BigDecimal actualCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    public static ProjectEntity createProjectFromAppointment(UUID appointmentId,BigDecimal estimatedCost,String type){
        ProjectEntity projectEntity=new ProjectEntity();
        projectEntity.status=ProjectStatus.CREATED;
        projectEntity.estimatedCost=estimatedCost;
        projectEntity.appointmentId=appointmentId;
        projectEntity.type=type;

        return projectEntity;

    }

    public void startProject(){
        if(this.status!=ProjectStatus.CREATED){
            throw new BusinessRuleException("Cant Strat a project that is not created");
        }

        this.status=ProjectStatus.IN_PROGRESS;
    }

    public void completeProject(BigDecimal actualCost){
        if(this.status!=ProjectStatus.IN_PROGRESS){
            throw new BusinessRuleException("Cant complete a project that is not in Progress");
        }

        this.status=ProjectStatus.COMPLETED;
        this.actualCost=actualCost;
    }

//    public void onHoldProject(){
//        if(this.status!=ProjectStatus.IN_PROGRESS){
//            throw new BusinessRuleException("Cant hold a project that is not in Progress");
//        }
//        this.status=ProjectStatus.ON_HOLD;
//    }


    public void cancelProject(){
        if(this.status==ProjectStatus.COMPLETED){
            throw new BusinessRuleException("cant cancel a completed project");
        }
        this.status=ProjectStatus.CANCELLED;
    }

    public void updateEstimate(BigDecimal estimatedCost){
        if(this.status==ProjectStatus.COMPLETED || this.status==ProjectStatus.CANCELLED){
            throw new BusinessRuleException("Cant update estimate due to project closed");
        }
        this.estimatedCost=estimatedCost;
    }
}


