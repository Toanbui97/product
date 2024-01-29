package vn.com.product.core.aop;

import feign.Util;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.weaver.Utils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.ContentCachingRequestWrapper;
import vn.com.product.core.utils.JSONParserUtils;
import vn.com.product.core.api.BaseRequest;
import vn.com.product.core.api.BaseResponse;
import vn.com.product.core.api.CommonHttpHeader;

@Aspect
@Configuration
@RequiredArgsConstructor
@Slf4j
public class RestApiLogging {

    private final HttpServletRequest httpRequest;
    private final HttpServletResponse httpResponse;

    @Resource(name = "commonHttpHeader")
    private CommonHttpHeader commonHttpHeader;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restApiPointCut() {
    }

    @Pointcut("within(vn.com.product.core.api.ResponseFactory)")
    public void responseEntityPointCut() {
    }

    @Before("restApiPointCut()")
    public void beforeCallApi(JoinPoint joinPoint) {
        var headerLogString = new StringBuilder();

        commonHttpHeader.getHeaderMap().forEach((key, value) ->
                headerLogString.append(StringTemplate.STR. "\{ key }= \{ value }" )
                        .append(System.lineSeparator()));
        var requestBody = getRequestBody(joinPoint);
        if (requestBody != null) {
            log.info("""

                            ---> Receive: {}.{}
                            {}
                            {}
                            """,
                    httpRequest.getMethod(), httpRequest.getRequestURL(),
                    headerLogString,
                    JSONParserUtils.writeValueAsString(requestBody));
        } else {
            log.info("""
                                                        
                            ---> Receive: {}.{}
                            {}
                            """,
                    httpRequest.getMethod(), httpRequest.getRequestURL(),
                    headerLogString);
        }
    }

    @AfterReturning(value = "responseEntityPointCut()", returning = "response")
    public void afterReturnResponse(ResponseEntity<?> response) {
        if (response != null && response.getBody() instanceof BaseResponse<?> body) {
            log.info("""
                                                        
                            <--- Response: {}.{}
                            {}
                            """
                    , httpRequest.getMethod(), httpRequest.getRequestURL(), JSONParserUtils.writeValueAsString(body));
        }
        commonHttpHeader.getHeaderMap().forEach(httpResponse::setHeader);
    }

    private String getBodyRaw(HttpServletRequest httpServletRequest) {
        var contentCached = new ContentCachingRequestWrapper(httpServletRequest);
        var bodyBytes = contentCached.getContentAsByteArray();
        return Util.decodeOrDefault(bodyBytes, Util.UTF_8, "Binary data");
    }

    private BaseRequest<?> getRequestBody(JoinPoint joinPoint) {
        var args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return null;
        }

        int index;
        BaseRequest<?> request = null;
        for (index = 0; index < args.length; index++) {
            if (args[index] instanceof BaseRequest<?> baseRequest) {
                request = baseRequest;
                break;
            }
        }
        return request;
    }
}
