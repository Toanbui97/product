package vn.com.product.core.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.com.product.core.factory.CommonHttpHeader;

@Component
@Slf4j
public class FeignClientInterceptor implements RequestInterceptor {

    @Resource(name = "commonHttpHeader")
    private CommonHttpHeader commonHttpHeader;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        commonHttpHeader.getHeaderMap().forEach(requestTemplate::header);
    }
}
