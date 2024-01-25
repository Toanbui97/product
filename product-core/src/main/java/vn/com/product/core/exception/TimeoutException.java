package vn.com.product.core.exception;

import lombok.Getter;
import vn.com.product.core.constant.ExceptionMessage;

@Getter
public class TimeoutException extends BaseException {
    public TimeoutException(ExceptionMessage exceptionMessage, String objects) {
        super(exceptionMessage, objects);
    }
}
