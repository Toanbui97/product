package vn.com.product.core.log;

import feign.Util;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.SerializationUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import vn.com.product.core.utils.JSONUtils;
import vn.com.product.core.api.BaseRequest;
import vn.com.product.core.api.CommonHttpHeader;

@Aspect
@Configuration
@RequiredArgsConstructor
@Slf4j
public class HttpResponseLogger {

    private final HttpServletRequest httpRequest;
    private final HttpServletResponse httpResponse;

    @Resource(name = "commonHttpHeader")
    private CommonHttpHeader commonHttpHeader;

    @Pointcut("within(vn.com.product.core.api.ResponseFactory)")
    public void responseEntityPointCut() {
    }

    @AfterReturning(value = "responseEntityPointCut()", returning = "response")
    public void afterReturnResponse(Object response) {
        if (response != null) {
            var body = "";
            var status = 200;
            if (response instanceof ResponseEntity<?> responseEntity) {
                body = JSONUtils.writeValueAsString(responseEntity.getBody());
                status = responseEntity.getStatusCode().value();
            } else {
                body = Util.decodeOrDefault(SerializationUtils.serialize(response), Util.UTF_8, "Binary data");
            }
            log.info("""

                            <--- Response: {}.{} - ({})
                            {}
                            """
                    , httpRequest.getMethod(), httpRequest.getRequestURL(), status, body);
        } else {
            log.info("""
                                                        
                            <--- Response with no body: {}.{} - ({})
                            """
                    , httpRequest.getMethod(), httpRequest.getRequestURL(), httpResponse.getStatus());
        }
    }
}
