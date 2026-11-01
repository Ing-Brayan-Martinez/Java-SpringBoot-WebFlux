package com.example.spring.service.impl;

import com.example.spring.domain.Order;
import com.example.spring.repository.OrderRepository;
import com.example.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del servicio de órdenes.
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Variable que almacena el repositorio de ordenes.
     */
    private final OrderRepository orderRepository;

    /**
     * Consructor de esta clase.
     * @param repo Repositorio de ordenes.
     */
    @Autowired
    public OrderServiceImpl(final OrderRepository repo) {
        this.orderRepository = repo;
    }

    /**
     * Método para obtener todas las órdenes.
     * @return Retorna una lista órdenes
     */
    @Override
    public Flux<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Método para obtener una orden segun su id.
     * @param id El id de la orden
     * @return Retorna la orden encontrada
     */
    @Override
    public Mono<Order> findOrderById(final Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Método para persistir una orden.
     * @param order La order para pesistir
     * @return Retorna la orden encontrada
     */
    @Override
    public Mono<Order> save(final Order order) {
        return orderRepository.save(order);
    }

    /**
     * Método para actualizar una orden.
     * @param order La order a actualizar
     * @return Retorna la orden encontrada
     */
    @Override
    public Mono<Order> edit(final Order order) {
        return orderRepository.findById(order.getOrderId())
        .switchIfEmpty(Mono.empty())
        .flatMap(origin -> {
            origin.setName(order.getName());
            origin.setAmount(order.getAmount());
            origin.setDiscount(order.getDiscount());
            return orderRepository.save(origin);
        });
    }

    /**
     * Método para eliminar una orden según su id.
     * @param id El id de la orden a eliminar
     * @return Retorna la orden encontrada
     */
    @Override
    public Mono<Void> delete(final Long id) {
        return orderRepository.deleteById(id);
    }
}
