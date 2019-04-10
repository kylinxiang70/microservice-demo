package org.kylin.productservice.exception;

public class ProductNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 8702783662718409707L;

    public ProductNotFoundException(String message) {
        super(message);
    }
}
