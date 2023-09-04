package com.learnspring.es.controller;

import com.learnspring.es.entity.Product;
import com.learnspring.es.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/es")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public Iterable<Product> getProducts() {
        return this.productService.getProducts();
    }
}
