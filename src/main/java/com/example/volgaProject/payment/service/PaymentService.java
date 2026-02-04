package com.example.volgaProject.payment.service;

import com.example.volgaProject.payment.dto.PaymentResponse;
import com.example.volgaProject.payment.enums.PaymentMode;
import com.example.volgaProject.payment.enums.PaymentType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PaymentService {

    //COMAND SERVICE
    PaymentResponse addPayment(UUID projectId, BigDecimal amount, PaymentType paymentType, PaymentMode paymentMode);

    //QURY SERVICE
    List<PaymentResponse> paymentByProject(UUID projectId);
    BigDecimal getTotalAmt(UUID projectId);
    BigDecimal getBalance(UUID projectId);
}
