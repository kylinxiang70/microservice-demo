package org.kylin.userservice.entity.exception;

public class UserOperationException extends RuntimeException {
    public UserOperationException(String msg) {
        super(msg);
    }
}
