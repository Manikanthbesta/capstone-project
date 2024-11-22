package com.example.Product.controller;

import com.example.Product.model.Product;
import com.example.Product.dto.ProductDTO;
import com.example.Product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Mono<ResponseEntity<Product>> createProduct(@RequestBody Product product) {
        return productService.createProduct(product)
                .map(createdProduct -> ResponseEntity.status(HttpStatus.CREATED).body(createdProduct));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/vendor/{id}")
    public Flux<ProductDTO> getProductByVendorId(@PathVariable String id) {
        return productService.getProductByVendorid(id);
    }

    @GetMapping("/category/{category}")
    public Flux<ProductDTO> getProductByCategory(@PathVariable String category) {
        return productService.getProductBycategory(category);
    }

    @GetMapping
    public Flux<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.updateProduct(id, product)
                .map(updatedProduct -> ResponseEntity.ok(updatedProduct))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }


}