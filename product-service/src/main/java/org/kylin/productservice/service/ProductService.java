package org.kylin.productservice.service;

import org.kylin.productservice.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(String id);

    void deleteById(String id);

    Product updateProduct(Product product);

    Product createProduct(Product product);

    List<Product> findAll();
}
