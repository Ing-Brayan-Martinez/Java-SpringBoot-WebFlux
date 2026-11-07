package com.example.spring.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de dominio para productos.
 */
@Data
@Table("product")
public final class Product {

    /**
     * Variable con el id del producto.
     */
    @Id
    private Long productId;

    /**
     * Variable con el nombre del producto.
     */
    @Column
    private String name;

    /**
     * Variable con el precio del producto.
     */
    @Column
    private BigDecimal price;

    /**
     * Variable con el stock del producto.
     */
    @Column
    private Integer stock;

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
