package com.example.spring.service;

import com.example.spring.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interfaz de servicio para productos.
 */
public interface ProductService {

    /**
     * Método para obtener todos los productos.
     * @return Retorna una lista productos
     */
    Flux<Product> findAllProducts();

    /**
     * Método para obtener un producto segun su id.
     * @param id El id del producto
     * @return Retorna el producto encontrado
     */
    Mono<Product> findProductById(Long id);

    /**
     * Método para persistir un producto.
     * @param product El producto para pesistir
     * @return Retorna el producto encontrado
     */
    Mono<Product> save(Product product);

    /**
     * Método para actualizar un producto.
     * @param product El producto a actualizar
     * @return Retorna el producto encontrado
     */
    Mono<Product> edit(Product product);

    /**
     * Método para eliminar un producto según su id.
     * @param id El id del producto a eliminar
     * @return Retorna la orden encontrada
     */
    Mono<Void> delete(Long id);

}
