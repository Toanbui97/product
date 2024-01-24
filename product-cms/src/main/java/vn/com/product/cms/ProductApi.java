package vn.com.product.cms;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class ProductApi {

    @Resource(name = "requestInformation") // Session scope
    private RequestInformation requestInformation;

    @GetMapping("/api/v1/prod/session-scopes")
    public ResponseEntity<String> test(@RequestBody TestSessionScopeReq req){

        CompletableFuture.runAsync(() -> this.test(requestInformation)); // throw exception
        return ResponseEntity.ok(req.toString());
    }

    public void test(RequestInformation requestInformation) {
        log.info(requestInformation.toString());
    }
}
