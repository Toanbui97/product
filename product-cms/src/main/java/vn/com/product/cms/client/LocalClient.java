package vn.com.product.cms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import vn.com.product.core.api.BaseResponse;

@FeignClient(name = "local", url = "http://localhost:8080")
public interface LocalClient {

    @GetMapping( "/api/v1/prod/success")
    BaseResponse<Object> success();

    @GetMapping( "/api/v1/prod/fail")
    BaseResponse<Object> fail();
}
