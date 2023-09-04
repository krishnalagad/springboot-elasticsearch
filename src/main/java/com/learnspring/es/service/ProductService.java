package com.learnspring.es.service;

import com.learnspring.es.entity.Product;
import com.learnspring.es.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;


    public Iterable<Product> getProducts() {
        return this.productRepo.findAll();
    }

    public Product getProduct(int id) {
        Product product = this.productRepo.findById(id).orElseThrow(() -> new RuntimeException(String.format("Product of ID %s is not " +
                "found.", String.valueOf(id))));
        return product;
    }

    public Product createProduct(Product product) {
        return this.productRepo.save(product);
    }

    public Product updateProduct(Product product, int id) {
        Product p = this.productRepo.findById(id).orElseThrow(() -> new RuntimeException(String.format("Product of ID" +
                " %s is not found.", String.valueOf(id))));

        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setQuantity(product.getQuantity());

        Product updatedProduct = this.productRepo.save(p);
        return updatedProduct;
    }

    public void deleteProduct(int id) {
        Product p = this.productRepo.findById(id).orElseThrow(() -> new RuntimeException(String.format("Product of ID" +
                " %s is not found.", String.valueOf(id))));
        this.productRepo.delete(p);
    }


}
