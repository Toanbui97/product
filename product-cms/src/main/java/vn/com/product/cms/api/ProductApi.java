package vn.com.product.cms.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.com.product.cms.dto.internal.TestRequest;
import vn.com.product.cms.client.LocalClient;
import vn.com.product.core.constant.ExceptionMessage;
import vn.com.product.core.exception.BusinessException;
import vn.com.product.core.api.BaseRequest;
import vn.com.product.core.api.BaseResponse;
import vn.com.product.core.api.ResponseFactory;
import vn.com.product.core.exception.FeignClientException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductApi {

    private final LocalClient localClient;
    private final ExecutorService executorService;
    private final ResponseFactory responseFactory;

    @GetMapping("/api/v1/prod/test-api")
    public ResponseEntity<BaseResponse<Object>> test(@RequestBody BaseRequest<TestRequest> req) {
        if ("success".equals(req.getParameters().type())) {
            CompletableFuture.runAsync(localClient::success, executorService).join();
        } else {
            CompletableFuture.runAsync(localClient::fail, executorService)
                    .exceptionally(throwable -> {
                        throw new FeignClientException(ExceptionMessage.EXCEPT0001, TestRequest.Fields.type);
                    }).join();
        }

        return responseFactory.success();
    }

    @GetMapping("/api/v1/prod/success")
    public ResponseEntity<BaseResponse<Object>> success() {
        return responseFactory.success();
    }

    @GetMapping("/api/v1/prod/fail")
    public ResponseEntity<BaseResponse<Object>> fail() {
        throw new BusinessException(ExceptionMessage.EXCEPT0001, "");
    }

}
