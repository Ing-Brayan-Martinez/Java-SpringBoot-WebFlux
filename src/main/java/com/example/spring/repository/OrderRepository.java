package com.example.spring.repository;

import com.example.spring.domain.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

    /**
     * Buscar todas las ordenes.
     * @return Todas las ordenes
     */
    @Query("SELECT * FROM \"order\"")
    Flux<Order> findAll();

}
