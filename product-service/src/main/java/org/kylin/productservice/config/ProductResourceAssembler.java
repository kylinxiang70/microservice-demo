package org.kylin.productservice.config;

import org.kylin.productservice.controller.ProductController;
import org.kylin.productservice.entity.Product;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Configuration
public class ProductResourceAssembler implements ResourceAssembler<Product, Resource<Product>> {
    @Override
    public Resource<Product> toResource(Product product) {
        return new Resource<>(product,
                linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAll()).withRel("products"));
    }
}
