package vn.com.product.core.api;

import lombok.Getter;

@Getter
public enum ResponseMessage {
    SUCCESS("success"),
    FAILED("failed");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

}
