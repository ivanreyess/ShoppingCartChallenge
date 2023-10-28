package com.sv.orderdetailservice.domain;

import com.sv.orderdetailservice.domain.dto.OrderDetailDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long orderId;

    private Integer quantity;

    private Double price;

    private Integer productId;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private long createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private long modifiedDate;

    public static OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        return OrderDetail.builder()
                .id(orderDetailDTO.id())
                .orderId(orderDetailDTO.orderId())
                .quantity(orderDetailDTO.quantity())
                .price(orderDetailDTO.price())
                .productId(orderDetailDTO.productId())
                .createdDate(orderDetailDTO.createdDate())
                .modifiedDate(orderDetailDTO.modifiedDate())
                .build();
    }

    public static OrderDetailDTO toDto(OrderDetail orderDetail) {
        return OrderDetailDTO.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrderId())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .productId(orderDetail.getProductId())
                .createdDate(orderDetail.getCreatedDate())
                .modifiedDate(orderDetail.getModifiedDate())
                .build();
    }

}
