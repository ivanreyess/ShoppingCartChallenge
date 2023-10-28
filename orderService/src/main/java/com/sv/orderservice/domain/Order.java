package com.sv.orderservice.domain;


import com.sv.orderservice.domain.dto.OrderDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String lastName;

    private Double total;

    private Double discount;

    private String city;

    private String country;

    @Email
    private String email;

    private String shippingAddress;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private long createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private long modifiedDate;

    public static OrderDTO toDto(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .name(order.getName())
                .lastName(order.getLastName())
                .total(order.getTotal())
                .discount(order.getDiscount())
                .city(order.getCountry())
                .country(order.getCountry())
                .email(order.getEmail())
                .shippingAddress(order.getShippingAddress())
                .createdDate(order.getCreatedDate())
                .modifiedDate(order.getModifiedDate())
                .build();
    }

    public static Order toEntity(OrderDTO orderDTO){
        return Order.builder()
                .id(orderDTO.id())
                .name(orderDTO.name())
                .lastName(orderDTO.lastName())
                .total(orderDTO.total())
                .discount(orderDTO.discount())
                .city(orderDTO.city())
                .country(orderDTO.country())
                .email(orderDTO.email())
                .shippingAddress(orderDTO.shippingAddress())
                .createdDate(orderDTO.createdDate())
                .modifiedDate(orderDTO.modifiedDate())
                .build();
    }


}
