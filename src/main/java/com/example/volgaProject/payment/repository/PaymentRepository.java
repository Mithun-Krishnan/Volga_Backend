package com.example.volgaProject.payment.repository;

import com.example.volgaProject.payment.entity.PaymentEntity;
import com.example.volgaProject.payment.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    List<PaymentEntity> findAllByProjectId(UUID projectId);
    @Query("select coalesce(sum(p.amount), 0) from PaymentEntity p where p.projectId =projectId")
    BigDecimal sumAmountByProjectId(UUID projectId);
    boolean existsByProjectIdAndPaymentType(UUID projectId, PaymentType paymentType);

}
