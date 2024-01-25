package vn.com.product.cms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.com.product.cms.client.LocalClient;
import vn.com.product.core.factory.BaseRequest;
import vn.com.product.core.factory.BaseResponse;
import vn.com.product.core.factory.ResponseFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductApi {

    private final LocalClient localClient;
    private final ExecutorService executorService;
    private final ResponseFactory responseFactory;

    @GetMapping("/api/v1/prod/session-scopes")
    public ResponseEntity<BaseResponse<Object>> test(@RequestBody BaseRequest<TestSessionScopeReq> req) {
        CompletableFuture.runAsync(localClient::callAsync, executorService).join();

        return responseFactory.success();
    }

    @GetMapping("/api/v1/prod/call-async")
    public ResponseEntity<BaseResponse<Object>> test1() {
        return responseFactory.success();
    }

}
