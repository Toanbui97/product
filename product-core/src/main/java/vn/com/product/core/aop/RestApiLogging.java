package vn.com.product.core.aop;

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
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import vn.com.product.core.utils.JSONParserUtils;
import vn.com.product.core.factory.BaseRequest;
import vn.com.product.core.factory.BaseResponse;
import vn.com.product.core.factory.CommonHttpHeader;

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

    @Before("restApiPointCut()")
    public void beforeCallApi(JoinPoint joinPoint) {
        var requestBody = getRequestBody(joinPoint);

        if (requestBody != null) {

            log.info("""

                            ===================> Receive: {}.{}
                            HttpHeader: {}
                            RequestBody: {}
                            """,
                    httpRequest.getMethod(), httpRequest.getRequestURL(),
                    JSONParserUtils.writeValueAsString(commonHttpHeader.getHeaderMap()),
                    JSONParserUtils.writeValueAsString(requestBody));
        } else {
            log.info("""
                            
                            ===================> Receive: {}.{}
                            HttpHeader: {}
                            """,
                    httpRequest.getMethod(), httpRequest.getRequestURL(),
                    JSONParserUtils.writeValueAsString(commonHttpHeader.getHeaderMap()));
        }
    }

    @AfterReturning(value = "restApiPointCut()", returning = "response")
    public void afterReturnResponse(JoinPoint joinPoint, ResponseEntity<?> response) {
        if (response != null && response.getBody() instanceof BaseResponse<?> body) {
            log.info("""
                            
                            ===================> Response: {}.{}
                            {}
                            """
                    , httpRequest.getMethod(), httpRequest.getRequestURL(), JSONParserUtils.writeValueAsString(body));
        }
        commonHttpHeader.getHeaderMap().forEach(httpResponse::setHeader);
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
