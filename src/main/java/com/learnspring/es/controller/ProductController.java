package com.learnspring.es.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.learnspring.es.entity.Product;
import com.learnspring.es.service.ElasticSearchService;
import com.learnspring.es.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Integer id) {
        this.productService.deleteProduct(id);
        Map<String, String> response = Map.of("message", "Product deleted successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/match-all")
    public String matchAll() throws IOException {
        SearchResponse<Map> searchResponse = this.elasticSearchService.matchAllService();
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse.hits().hits().toString();
    }

    @GetMapping("/match-all-products")
    public ResponseEntity<List<Product>> matchAllProducts() throws Exception {
        SearchResponse<Product> searchResponse = this.elasticSearchService.matchAllProductsService();
        List<Hit<Product>> listOfHits = searchResponse.hits().hits();
        List<Product> listOfProducts = new ArrayList<>();

        for (Hit<Product> hit : listOfHits) {
            listOfProducts.add(hit.source());
        }

        return ResponseEntity.ok(listOfProducts);
    }

    @GetMapping("/match-name/{name}")
    public ResponseEntity<List<Product>> matchWithName(@PathVariable String name) throws Exception {
        SearchResponse<Product> searchResponse = this.elasticSearchService.matchProductWithName(name);
        List<Hit<Product>> listOfHits = searchResponse.hits().hits();
        List<Product> listOfProducts = new ArrayList<>();

        for (Hit<Product> hit : listOfHits) {
            listOfProducts.add(hit.source());
        }
        return ResponseEntity.ok(listOfProducts);
    }

    @GetMapping("/fuzzy-query/{name}")
    public ResponseEntity<List<Product>> matchWithFuzzyQuery(@PathVariable String name) throws IOException {
        SearchResponse<Product> searchResponse = this.elasticSearchService.matchProductWithFuzzyName(name);
        List<Hit<Product>> listOfHits = searchResponse.hits().hits();
        List<Product> listOfProducts = new ArrayList<>();

        for (Hit<Product> hit : listOfHits) {
            listOfProducts.add(hit.source());
        }
        return ResponseEntity.ok(listOfProducts);
    }
}
