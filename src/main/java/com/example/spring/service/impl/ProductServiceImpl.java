package com.example.spring.service.impl;

import com.example.spring.domain.Product;
import com.example.spring.repository.ProductRepository;
import com.example.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Implementación del servicio de productos.
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * Variable que almacena el repositorio de productos.
     */
    private final ProductRepository productRepository;

    /**
     * Consructor de esta clase.
     * @param repo Repositorio de productos.
     */
    @Autowired
    public ProductServiceImpl(final ProductRepository repo) {
        this.productRepository = repo;
    }

    /**
     * Método para obtener todos los productos.
     * @return Retorna una lista productos
     */
    @Override
    public Flux<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Método para obtener un producto segun su id.
     * @param id El id del producto
     * @return Retorna el producto encontrado
     */
    @Override
    public Mono<Product> findProductById(final Long id) {
        return productRepository.findById(id);
    }

    /**
     * Método para persistir un producto.
     * @param product El producto para pesistir
     * @return Retorna el producto encontrado
     */
    @Override
    public Mono<Product> save(final Product product) {
        if (product.getCreatedAt() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }
        if (product.getUpdatedAt() == null) {
            product.setUpdatedAt(LocalDateTime.now());
        }
        return productRepository.save(product);
    }

    /**
     * Método para actualizar un producto.
     * @param product El producto a actualizar
     * @return Retorna el producto encontrado
     */
    @Override
    public Mono<Product> edit(final Product product) {
        return productRepository.findById(product.getProductId())
                .switchIfEmpty(Mono.empty())
                .flatMap(origin -> {
                    origin.setName(product.getName());
                    origin.setPrice(product.getPrice());
                    origin.setStock(product.getStock());
                    origin.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(origin);
                });
    }

    /**
     * Método para eliminar un producto según su id.
     * @param id El id del producto a eliminar
     * @return Retorna la orden encontrada
     */
    @Override
    public Mono<Void> delete(final Long id) {
        return productRepository.deleteById(id);
    }
}
