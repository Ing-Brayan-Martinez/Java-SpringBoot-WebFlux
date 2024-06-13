package com.example.spring.service;

import com.example.spring.domain.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface OrderService {

    /**
     * Método para obtener todas las órdenes.
     * @return Retorna una lista órdenes
     */
    Flux<Order> findAllOrders();

    /**
     * Método para obtener una orden segun su id.
     * @param id El id de la orden
     * @return Retorna la orden encontrada
     */
    Mono<Order> findOrderById(Long id);

    /**
     * Método para persistir una orden.
     * @param order La order para pesistir
     * @return Retorna la orden encontrada
     */
    Mono<Order> save(Order order);

    /**
     * Método para actualizar una orden.
     * @param order La order a actualizar
     * @return Retorna la orden encontrada
     */
    Mono<Order> edit(Order order);

    /**
     * Método para eliminar una orden según su id.
     * @param id El id de la orden a eliminar
     * @return Retorna la orden encontrada
     */
    Mono<Void> delete(Long id);


}
