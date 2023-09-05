package com.learnspring.es.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.learnspring.es.entity.Product;
import com.learnspring.es.service.ElasticSearchService;
import com.learnspring.es.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/es")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @PostMapping("/")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product savedProduct = this.productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/")
    public Iterable<Product> getProducts() {
        return this.productService.getProducts();
    }

    @GetMapping("/match-all")
    public SearchResponse<Map> matchAll() throws IOException {
        SearchResponse<Map> searchResponse = this.elasticSearchService.matchAllService();
        return searchResponse;
    }
}
