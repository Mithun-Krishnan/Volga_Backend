package com.example.volgaProject.common.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public  abstract class BaseEntity {

    @Id
    @GeneratedValue
    @Column(nullable = false,updatable = false)
    UUID id;

    @Column(nullable = false,updatable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }


}
