package vn.com.product.cms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@RestController
@Slf4j
public class ProductApi {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private ExecutorService executors;

    @Autowired
    private LocalClient localClient;

    @GetMapping("/api/v1/prod/session-scopes")
    public ResponseEntity<String> test(@RequestBody TestSessionScopeReq req) {

        var requestInfo = HttpRequestUtils.getRequestInfo(httpServletRequest);
        CompletableFuture.runAsync(() -> {
            var clientResponse = localClient.callAsync(HttpRequestUtils.getRequestInfo(httpServletRequest));
            try {
                log.info(new ObjectMapper().writeValueAsString(clientResponse));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }, executors).join();

        return ResponseEntity.ok(req.toString());
    }

    @GetMapping("/api/v1/prod/call-async")
    public ResponseEntity<BaseResponse<Object>> test1() throws JsonProcessingException {
        var requestInfo = HttpRequestUtils.getRequestInfo(httpServletRequest);
        log.info(new ObjectMapper().writeValueAsString(requestInfo));
        new BaseResponse<String>();
        return ResponseEntity.status(HttpStatus.OK)
                .headers(httpHeaders -> HttpRequestUtils.HEADER_NAME_SET.forEach(s -> httpHeaders.set(s, (String) requestInfo.get(s))))
                .body(BaseResponse.builder()
                        .code("5001")
                        .message("failed")
                        .errorMessage("errors")
                        .data(null)
                        .build());
    }

    @SneakyThrows
    public void test(Map<String, String> header) {
        log.info(new ObjectMapper().writeValueAsString(header));
    }
}
