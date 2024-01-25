package vn.com.product.core.exception;

import lombok.Getter;
import vn.com.product.core.constant.ExceptionMessage;

@Getter
public class BusinessException extends BaseException {

    public BusinessException(ExceptionMessage exceptionMessage, String objects) {
        super(exceptionMessage, objects);
    }
}
