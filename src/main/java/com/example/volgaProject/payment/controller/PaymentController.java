package com.example.volgaProject.payment.controller;

import com.example.volgaProject.payment.dto.PaymentRequstDto;
import com.example.volgaProject.payment.dto.PaymentResponse;
import com.example.volgaProject.payment.entity.PaymentEntity;
import com.example.volgaProject.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> addPayment(@RequestBody PaymentRequstDto requst){
        System.out.println(requst);
        return ResponseEntity.ok(paymentService.addPayment(
                requst.getProjectId(),
                requst.getAmount(),
                requst.getPaymentType(),
                requst.getPaymentMode()));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentByProject(@PathVariable UUID projectId){
        List<PaymentResponse> responses=paymentService.paymentByProject(projectId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/project/{projectId}/total")
    public ResponseEntity<BigDecimal> getTotal(@PathVariable UUID projectId){
        return ResponseEntity.ok(paymentService.getTotalAmt(projectId));
    }


    @GetMapping("/project/{projectId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID projectId) {
        return ResponseEntity.ok(paymentService.getBalance(projectId));
    }


}
