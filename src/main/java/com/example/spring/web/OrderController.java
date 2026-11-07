package com.example.spring.web;

import com.example.spring.domain.Order;
import com.example.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Controlador REST para ordenes.
 */
@RestController
@RequestMapping("/api/v1")
public final class OrderController {

    /**
     * Variable que almacena un servicio.
     */
    private final OrderService orderService;

    /**
     * Consructor de esta clase.
     * @param service Servicio de ordenes.
     */
    @Autowired
    public OrderController(final OrderService service) {
        this.orderService = service;
    }

    /**
     * Método que escucha la URI /orders .
     * @return Retorna una lista de ordenes
     */
    @GetMapping("/order")
    public Flux<Order> index() {
        return this.orderService.findAllOrders();
    }

}
