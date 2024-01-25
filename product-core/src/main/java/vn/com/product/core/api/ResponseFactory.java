package vn.com.product.core.api;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vn.com.product.core.exception.BaseException;


@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseFactory {

    @Resource(name = "commonHttpHeader")
    private CommonHttpHeader commonHttpHeader;

    public ResponseEntity<BaseResponse<Object>> success() {
        return ResponseEntity.ok( new BaseResponse<>(0, ResponseMessage.SUCCESS.getMessage(),
                commonHttpHeader.getTraceId()));
    }

    public <T> ResponseEntity<BaseResponse<T>> success(T data) {
        return ResponseEntity.ok(new BaseResponse<>(0, ResponseMessage.SUCCESS.getMessage(),
                commonHttpHeader.getTraceId(), data));
    }

    public <T, E extends BaseException> ResponseEntity<BaseResponse<T>> fail(T data, HttpStatusCode statusCode, E exception) {
        return ResponseEntity.status(statusCode)
                .body(new BaseResponse<>(1, exception.getExceptionMessage().getValue(),
                        exception.getExceptionMessage().getKey(),
                        commonHttpHeader.getTraceId(), exception.getErrorField(),
                        data));
    }
}
