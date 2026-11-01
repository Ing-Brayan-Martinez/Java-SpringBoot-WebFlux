package com.example.spring.repository;

import com.example.spring.domain.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Repositorio reactivo para productos.
 */
@Repository
public interface ProductRepository
        extends ReactiveCrudRepository<Product, Long> {

    /**
     * Buscar todos los productos.
     * @return Todos los productos
     */
    @Query("SELECT * FROM \"product\"")
    Flux<Product> findAll();

}
