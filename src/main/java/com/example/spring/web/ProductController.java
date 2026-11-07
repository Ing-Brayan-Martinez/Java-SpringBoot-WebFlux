package com.example.spring.web;

import com.example.spring.domain.Product;
import com.example.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST de productos.
 */
@RestController
@RequestMapping("/api/v1")
public final class ProductController {

    /**
     * Variable que almacena un servicio.
     */
    private final ProductService productService;

    /**
     * Consructor de esta clase.
     * @param service Servicio de productos.
     */
    @Autowired
    public ProductController(final ProductService service) {
        this.productService = service;
    }

    /**
     * Método que escucha la URI /product .
     * @return Retorna una lista de productos
     */
    @GetMapping("/product")
    public Flux<Product> index() {
        return this.productService.findAllProducts();
    }

    /**
     * Método para obtener un producto por id.
     * @param id El id del producto
     * @return Retorna el producto encontrado
     */
    @GetMapping("/product/{id}")
    public Mono<Product> findById(@PathVariable final Long id) {
        return this.productService.findProductById(id);
    }

    /**
     * Método para crear un nuevo producto.
     * @param product El producto a guardar
     * @return Retorna el producto guardado
     */
    @PostMapping("/product")
    public Mono<Product> save(@RequestBody final Product product) {
        return this.productService.save(product);
    }

    /**
     * Método para actualizar un producto existente.
     * @param id El id del producto
     * @param product El producto con los datos actualizados
     * @return Retorna el producto actualizado
     */
    @PutMapping("/product/{id}")
    public Mono<Product> update(
            @PathVariable final Long id,
            @RequestBody final Product product) {
        product.setProductId(id);
        return this.productService.edit(product);
    }

    /**
     * Método para eliminar un producto por su id.
     * @param id El id del producto a eliminar
     * @return Retorna Mono vacio
     */
    @DeleteMapping("/product/{id}")
    public Mono<Void> delete(@PathVariable final Long id) {
        return this.productService.delete(id);
    }

}
