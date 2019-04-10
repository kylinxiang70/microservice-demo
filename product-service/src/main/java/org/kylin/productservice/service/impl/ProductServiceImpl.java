package org.kylin.productservice.service.impl;

import org.kylin.productservice.entity.Product;
import org.kylin.productservice.exception.ProductNotFoundException;
import org.kylin.productservice.repository.ProductRepository;
import org.kylin.productservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found: id" + id));
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
