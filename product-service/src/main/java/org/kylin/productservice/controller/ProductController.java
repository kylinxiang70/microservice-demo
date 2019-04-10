package org.kylin.productservice.controller;

import org.kylin.productservice.config.ProductResourceAssembler;
import org.kylin.productservice.entity.Product;
import org.kylin.productservice.service.ProductService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private ProductResourceAssembler productResourceAssembler;

    public ProductController(ProductService productService, ProductResourceAssembler productResourceAssembler) {
        this.productService = productService;
        this.productResourceAssembler = productResourceAssembler;
    }

    @PostMapping
    public Resource<Product> createProduct(@RequestBody Product product) {
        return productResourceAssembler.toResource(productService.createProduct(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id) {
        productService.deleteById(id);
        return ok().build();
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return ok().body(productService.updateProduct(product));
    }

    @GetMapping("/{id}")
    public Resource<Product> getProductById(@PathVariable String id) {
        return productResourceAssembler.toResource(productService.getProductById(id));
    }

    @GetMapping
    public Resources<Resource<Product>> getAll() {
        List<Resource<Product>> products = productService.findAll().stream()
                .map(productResourceAssembler::toResource).collect(Collectors.toList());
        return new Resources<>(products, linkTo(methodOn(ProductController.class).getAll()).withSelfRel());
    }
}
