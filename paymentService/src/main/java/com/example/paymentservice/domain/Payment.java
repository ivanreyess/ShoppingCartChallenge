package com.example.paymentservice.domain;

import com.sv.service.payment.dto.PaymentDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "total")
    private Double total;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private long createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private long modifiedDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    public static PaymentDTO toDto(Payment payment){
        return PaymentDTO.builder()
                .id(payment.getId())
                .status(payment.getStatus())
                .total(payment.getTotal())
                .orderId(payment.getOrderId())
                .createdDate(payment.getCreatedDate())
                .modifiedDate(payment.getModifiedDate())
                .build();
    }


   public static Payment toEntity(PaymentDTO paymentDTO){
        return Payment.builder()
                .id(paymentDTO.id())
                .status(paymentDTO.status())
                .total(paymentDTO.total())
                .orderId(paymentDTO.orderId())
                .createdDate(paymentDTO.createdDate())
                .modifiedDate(paymentDTO.modifiedDate())
                .build();
   }


}
