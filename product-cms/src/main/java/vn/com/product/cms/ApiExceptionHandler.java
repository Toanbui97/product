package vn.com.product.cms;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    @ExceptionHandler(WebClientException.class)
    public final ResponseEntity<BaseResponse<Object>> handleSecurityException(WebClientException exception) {
        BaseResponse<Object> response = new BaseResponse<>(exception.getCode(),
                null,
                exception.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
