package vn.com.product.core.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.com.product.core.api.BaseResponse;
import vn.com.product.core.api.ResponseFactory;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestApiExceptionHandler {

    private final ResponseFactory responseFactory;

    @ExceptionHandler(value = BusinessException.class)
    ResponseEntity<BaseResponse<Void>> handleException(BusinessException exception) {
        return responseFactory.fail(null, HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception);
    }

    @ExceptionHandler(value = FeignClientException.class)
    ResponseEntity<BaseResponse<Void>> handleException(FeignClientException exception) {
        return responseFactory.fail(null, HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception);
    }

    @ExceptionHandler(value = TimeoutException.class)
    ResponseEntity<BaseResponse<Void>> handleException(TimeoutException exception) {
        return responseFactory.fail(null, HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception);
    }
}
