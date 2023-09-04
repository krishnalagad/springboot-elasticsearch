package com.learnspring.es.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int id;

    private String name;

    private String description;

    private int quantity;

    private double price;
}
