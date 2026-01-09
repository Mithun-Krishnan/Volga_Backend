package com.example.volgaProject.project.repository;

import com.example.volgaProject.project.dto.ProjectWithClientDTO;
import com.example.volgaProject.project.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
    @Query("""
        SELECT new com.example.volgaProject.project.dto.ProjectWithClientDTO(
            p.id,
            p.type,
            p.status,
            p.estimatedCost,
            c.name,
            c.phone
        )
        FROM ProjectEntity p
        JOIN AppointmentEntity a ON a.clientId = p.clientId
        JOIN ClientEntity c ON c.id = p.clientId
        WHERE p.id = :projectId
    """)
    Optional<ProjectWithClientDTO> findProjectWithClient(UUID projectId);

}
