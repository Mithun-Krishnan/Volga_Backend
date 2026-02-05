package com.example.volgaProject.client.entity;

import com.example.volgaProject.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class ClientEntity extends BaseEntity {


    @Column(nullable = false)
    private String  name;

    @Column(nullable = false)
    private String phone;

    @Column
    private String email;

    @Column
    private String address;
}
