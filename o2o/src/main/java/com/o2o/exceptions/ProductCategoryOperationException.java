package com.o2o.exceptions;

public class ProductCategoryOperationException extends RuntimeException {

    private static final long serialVersionUID = -1712944541812052571L;

    public ProductCategoryOperationException(String msg) {
        super(msg);
    }
}
