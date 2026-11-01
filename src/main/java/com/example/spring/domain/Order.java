package com.example.spring.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("order")
public final class Order {

    /**
     * Variable con el id de la orden.
     */
    @Id
    private Long orderId;

    /**
     * Variable con el nombre de la orden.
     */
    @Column
    private String name;

    /**
     * Variable con el monto de la orden.
     */
    @Column
    private BigDecimal amount;

    /**
     * Variable con el descuento de la orden.
     */
    @Column
    private BigDecimal discount;

    /**
     * Variable con la fecha de creacion.
     */
    @Column
    private LocalDateTime createdAt;

    /**
     * Variable con la fecha de actualización.
     */
    @Column
    private LocalDateTime updatedAt;

}
