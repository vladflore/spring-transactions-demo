package com.example.demo;

public class Preconditions {

    private static final Preconditions INSTANCE = new Preconditions();

    private Preconditions() {
    }

    public static Preconditions getInstance() {
        return INSTANCE;
    }

    public void checkValidQuantityThrowRuntimeException(Long quantity) {
        if (quantity < 0) {
            throw new QuantityRuntimeException(String.format("Invalid quantity: %d", quantity));
        }
    }

    public void checkValidQuantityThrowException(Long quantity) throws QuantityException {
        if (quantity < 0) {
            throw new QuantityException(String.format("Invalid quantity: %d", quantity));
        }
    }
}
