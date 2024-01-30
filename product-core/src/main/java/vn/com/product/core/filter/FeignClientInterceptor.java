package vn.com.product.core.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vn.com.product.core.api.CommonHttpHeader;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Resource(name = "commonHttpHeader")
    private CommonHttpHeader commonHttpHeader;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        commonHttpHeader.getHeaderMap().forEach((key, value) -> {
            if (StringUtils.hasText(value)) {
                requestTemplate.header(key, value);
            }
        });
    }
}
