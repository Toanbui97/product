package vn.com.product.core.exception;

import lombok.Getter;

@Getter
public class FeignClientException extends RuntimeException {
    private final transient String fieldName;
    private final transient Object[] objects;
    private final transient String message;

    public FeignClientException(String fieldName, String message, Object... objects) {
        super("FeignClientException");
        this.fieldName = fieldName;
        this.message = message;
        this.objects = objects;
    }

    public FeignClientException(String message) {
        super("FeignClientException");
        this.fieldName = null;
        this.message = message;
        this.objects = null;
    }
}
