package com.example.volgaProject.payment.service;

import com.example.volgaProject.exception.BusinessRuleException;
import com.example.volgaProject.payment.dto.PaymentResponse;
import com.example.volgaProject.payment.entity.PaymentEntity;
import com.example.volgaProject.payment.enums.PaymentMode;
import com.example.volgaProject.payment.enums.PaymentType;
import com.example.volgaProject.payment.repository.PaymentRepository;
import com.example.volgaProject.project.enums.ProjectStatus;
import com.example.volgaProject.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService{

    final PaymentRepository paymentRepository;
    final ProjectService projectService;

    @Override
    public PaymentResponse addPayment(UUID projectId, BigDecimal amount, PaymentType paymentType, PaymentMode paymentMode) {

        if(amount.compareTo(BigDecimal.ZERO)<=0){
            throw new BusinessRuleException("Payment amount must be greater than zero");
        }

        ProjectStatus projectStatus=projectService.getStatus(projectId);
        if(projectStatus!=ProjectStatus.IN_PROGRESS && projectStatus !=ProjectStatus.COMPLETED){
            throw new BusinessRuleException("Cant pay if project  is not in progress or completed");
        }

        boolean finalExist =paymentRepository.existsByProjectIdAndPaymentType(projectId,PaymentType.FINAL);
        if(finalExist){
            throw new BusinessRuleException("Final payment already done. No more payments allowed.");
        }

        if (paymentType == PaymentType.ADVANCE) {
            boolean advanceExists = paymentRepository.existsByProjectIdAndPaymentType(projectId, PaymentType.ADVANCE);
            if (advanceExists) {
                throw new BusinessRuleException("Advance payment already done. Only one advance is allowed.");
            }
        }


        BigDecimal totalPaid=getTotalAmt(projectId);
        BigDecimal actual=projectService.getActualAmount(projectId);
        BigDecimal estimate=projectService.getEstimatedAmount(projectId);
        BigDecimal limit=(actual==null)?estimate:actual;
        if (limit == null) {
            throw new BusinessRuleException("Project cost is not defined yet");
        }

        BigDecimal remaining=limit.subtract(totalPaid);

        if(remaining.compareTo(BigDecimal.ZERO)<=0){
            throw new BusinessRuleException("fully payed");
        }

        if(amount.compareTo(remaining)>0){
            throw new BusinessRuleException("cant pay more than actual amount");
        }

        if(paymentType==PaymentType.FINAL && amount.compareTo(remaining)!=0){
            throw new BusinessRuleException("Final payment must settle the exact remaining amount");

        }





        PaymentEntity paymentEntity=PaymentEntity.create(projectId,amount,paymentType,paymentMode, LocalDateTime.now());
        PaymentEntity saved=paymentRepository.save(paymentEntity);

        PaymentResponse response=PaymentResponse.builder()
                .id(saved.getId())
                .type(saved.getPaymentType())
                .mode(saved.getPaymentMode())
                .paidAt(saved.getPaidAt())
                .amount(saved.getAmount())
                .build();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> paymentByProject(UUID projectId) {
        List<PaymentEntity> paymentEntityList=paymentRepository.findAllByProjectId(projectId);
        List<PaymentResponse> response=paymentEntityList.stream()
                .map(paymentEntity -> new PaymentResponse(
                        paymentEntity.getId(),
                        paymentEntity.getProjectId(),
                        paymentEntity.getAmount(),
                        paymentEntity.getPaymentType(),
                        paymentEntity.getPaymentMode(),
                        paymentEntity.getPaidAt()
                )).toList();
        return response;
    }

    @Override
    public BigDecimal getTotalAmt(UUID projectId) {
        BigDecimal total=paymentRepository.sumAmountByProjectId(projectId);
        return total==null?BigDecimal.ZERO:total;
    }

    @Override
    public BigDecimal getBalance(UUID projectId) {
        BigDecimal totalPaid = getTotalAmt(projectId);

        BigDecimal actual = projectService.getActualAmount(projectId);
        BigDecimal estimate = projectService.getEstimatedAmount(projectId);

        BigDecimal limit = (actual != null) ? actual : estimate;
        if (limit == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal balance = limit.subtract(totalPaid);
        return balance.max(BigDecimal.ZERO);
    }
}
