package vn.com.product.cms;


import feign.HeaderMap;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "local", url = "http://localhost:8080")
public interface LocalClient {

    @RequestMapping(value = "/api/v1/prod/call-async", method = RequestMethod.GET)
    BaseResponse<Object> callAsync(@RequestHeader Map<String, Object> header);
}
