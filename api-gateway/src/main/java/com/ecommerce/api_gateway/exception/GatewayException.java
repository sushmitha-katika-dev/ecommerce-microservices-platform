package com.ecommerce.api_gateway.exception;

public class GatewayException extends RuntimeException {

    private final int status;

    public GatewayException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
