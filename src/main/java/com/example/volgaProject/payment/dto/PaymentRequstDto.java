package com.example.volgaProject.payment.dto;

import com.example.volgaProject.payment.enums.PaymentMode;
import com.example.volgaProject.payment.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequstDto {
    private UUID projectId;
    private BigDecimal amount;
    private PaymentType paymentType;
    private PaymentMode paymentMode;
}
