package com.example.spring.web;

import com.example.spring.domain.Order;
import com.example.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1")
public final class OrderController {

    /**
     * Variable que almacena un servicio.
     */
    private OrderService orderService;

    /**
     * Consructor de esta clase.
     * @param orderService Servicio de ordenes.
     */
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
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
