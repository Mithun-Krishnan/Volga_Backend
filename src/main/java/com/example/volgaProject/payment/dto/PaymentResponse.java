package com.example.volgaProject.payment.dto;

import com.example.volgaProject.payment.enums.PaymentMode;
import com.example.volgaProject.payment.enums.PaymentType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PaymentResponse {

    public PaymentResponse(UUID id,UUID projectId,BigDecimal amount,PaymentType type,PaymentMode mode,LocalDateTime paidAt){
        this.id=id;
        this.amount=amount;
        this.mode=mode;
        this.type=type;
        this.paidAt=paidAt;
        this.projectId=projectId;
    }

    private UUID id;
    private UUID projectId;
    private BigDecimal amount;
    private PaymentType type;
    private PaymentMode mode;
    private LocalDateTime paidAt;
}
