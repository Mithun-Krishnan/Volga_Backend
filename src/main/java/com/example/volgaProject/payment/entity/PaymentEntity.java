package com.example.volgaProject.payment.entity;

import com.example.volgaProject.common.entity.BaseEntity;
import com.example.volgaProject.payment.enums.PaymentMode;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(name = "idx_payment_project_id",columnList = "projectId"),
                @Index(name = "idx_payment_date",columnList = "paymentDate")
        }

)
public class PaymentEntity extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private UUID projectId;

    @Column(nullable = false, updatable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private PaymentMode paymentMode;

    @Column(nullable = false, updatable = false)
    private LocalDate paymentDate;

}
