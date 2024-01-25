package vn.com.product.core.api;

public enum ResponseMessage {
    SUCCESS("success"),
    FAILED("failed");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
