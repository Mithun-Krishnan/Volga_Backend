package com.example.volgaProject.payment.entity;

import com.example.volgaProject.common.entity.BaseEntity;
import com.example.volgaProject.payment.enums.PaymentMode;
import com.example.volgaProject.payment.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(name = "idx_payment_project_id",columnList = "projectId"),
                @Index(name = "idx_paidAt",columnList = "paidAt")
        }

)
@Getter
public class PaymentEntity extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private UUID projectId;

    @Column(nullable = false, updatable = true)
    private BigDecimal amount;

//    @Column(nullable = false)
//    private BigDecimal estimateCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private PaymentMode paymentMode;


    @Column(nullable = false, updatable = false)
    private LocalDateTime paidAt;

    public static PaymentEntity create(UUID projectId,
                                       BigDecimal amount,
                                       PaymentType type,
                                       PaymentMode mode,
                                       LocalDateTime paidAt) {

        if (projectId == null) throw new IllegalArgumentException("projectId cannot be null");
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("amount must be > 0");
        if (type == null) throw new IllegalArgumentException("type cannot be null");
        if (mode == null) throw new IllegalArgumentException("mode cannot be null");
        if (paidAt == null) throw new IllegalArgumentException("paidAt cannot be null");

       PaymentEntity paymentEntity=new PaymentEntity();
       paymentEntity.paymentMode=mode;
       paymentEntity.paymentType=type;
       paymentEntity.paidAt=paidAt;
       paymentEntity.amount=amount;
       paymentEntity.projectId=projectId;
       return paymentEntity;
    }

}
