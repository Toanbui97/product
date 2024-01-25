package vn.com.product.core.factory;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseFactory {

    @Resource(name = "commonHttpHeader")
    private CommonHttpHeader commonHttpHeader;

    public ResponseEntity<BaseResponse<Object>> success() {
        return ResponseEntity.ok( new BaseResponse<>(0, ResponseMessage.SUCCESS.getMessage(), commonHttpHeader.getTraceId()));
    }

    public <T> ResponseEntity<BaseResponse<T>> success(T data) {
        return ResponseEntity.ok(new BaseResponse<>(0, ResponseMessage.SUCCESS.getMessage(), commonHttpHeader.getTraceId(), data));
    }

}
