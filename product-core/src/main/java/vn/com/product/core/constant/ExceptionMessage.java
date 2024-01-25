package vn.com.product.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    EXCEPT0001("EXCEPT0001", "Internal server error");

    private String key;
    private String value;

    ExceptionMessage(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
