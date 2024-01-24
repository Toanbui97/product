package vn.com.product.cms;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class HttpRequestAop {

    private final HttpSession httpSession;
    private final HttpServletRequest httpServletRequest;


    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restApiPointCut() {
    }

    @Before("restApiPointCut()")
    public void beforeCallApi() {
        var requestInformation = new RequestInformation();
        requestInformation.setRequestId(httpServletRequest.getHeader("requestId"));
        requestInformation.setRef(httpServletRequest.getHeader("ref"));
        requestInformation.setUserId(httpServletRequest.getHeader("userId"));
        httpSession.setAttribute("requestInformation", requestInformation);
    }
}
