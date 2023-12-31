package com.learnspring.es.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.learnspring.es.entity.Product;
import com.learnspring.es.util.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class ElasticSearchService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public SearchResponse<Map> matchAllService() throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplier();
        SearchResponse<Map> searchResponse = this.elasticsearchClient.search(s -> s.query(supplier.get()), Map.class);
        System.out.println("Elasticsearch query: " + supplier.get().toString());
        return searchResponse;
    }

    public SearchResponse<Product> matchAllProductsService() throws Exception {
        Supplier<Query> supplier = ElasticSearchUtil.supplier();
        SearchResponse<Product> searchResponse =
                this.elasticsearchClient.search(s -> s.index("products").query(supplier.get()), Product.class);
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse;
    }

    public SearchResponse<Product> matchProductWithName(String name) throws Exception {
        Supplier<Query> supplier = ElasticSearchUtil.supplierWithNameField(name);
        SearchResponse<Product> searchResponse =
                this.elasticsearchClient.search(s -> s.index("products").query(supplier.get()), Product.class);
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse;
    }

    public SearchResponse<Product> matchProductWithFuzzyName(String approximateProductName) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplierWithFuzzyString(approximateProductName);
        SearchResponse<Product> searchResponse =
                this.elasticsearchClient.search(s -> s.index("products").query(supplier.get()), Product.class);
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse;
    }
}
