package vn.com.product.core.exception;

import lombok.Getter;
import vn.com.product.core.constant.ExceptionMessage;

@Getter
public class FeignClientException extends BaseException {
    public FeignClientException(ExceptionMessage exceptionMessage, String objects) {
        super(exceptionMessage, objects);
    }
}
