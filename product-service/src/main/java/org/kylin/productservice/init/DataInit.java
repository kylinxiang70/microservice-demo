package org.kylin.productservice.init;

import org.kylin.productservice.entity.Product;
import org.kylin.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInit implements CommandLineRunner {
    private ProductRepository productRepository;

    public DataInit(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        productRepository.saveAll(Arrays.asList(
                Product.builder().name("cloth").price(300).build(),
                Product.builder().name("sock").price(20).build()
        ));
    }
}
