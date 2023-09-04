package com.learnspring.es.controller;

import com.learnspring.es.entity.Product;
import com.learnspring.es.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/es")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product savedProduct = this.productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/")
    public Iterable<Product> getProducts() {
        return this.productService.getProducts();
    }
}
