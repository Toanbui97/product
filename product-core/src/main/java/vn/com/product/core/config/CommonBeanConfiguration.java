package vn.com.product.core.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.DispatcherServlet;
import vn.com.product.core.api.CommonHttpHeader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class CommonBeanConfiguration {

    @Bean
    @SessionScope
    public CommonHttpHeader commonHttpHeader(HttpServletRequest httpServletRequest) {
        var commonHeader = new CommonHttpHeader();
        commonHeader.setTraceId(httpServletRequest.getHeader("trace-id"));
        commonHeader.setRef(httpServletRequest.getHeader("ref"));
        commonHeader.setUserId(httpServletRequest.getHeader("user-id"));
        commonHeader.setLocale(StringUtils.hasText(httpServletRequest.getHeader("locale")) ?
                httpServletRequest.getHeader("locale") : "en");
        return commonHeader;
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newThreadPerTaskExecutor(Thread.ofVirtual().inheritInheritableThreadLocals(true).factory());
    }

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet(WebMvcProperties webMvcProperties) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setThreadContextInheritable(true);
        dispatcherServlet.setDispatchOptionsRequest(webMvcProperties.isDispatchOptionsRequest());
        dispatcherServlet.setDispatchTraceRequest(webMvcProperties.isDispatchTraceRequest());
        dispatcherServlet.setPublishEvents(webMvcProperties.isPublishRequestHandledEvents());
        dispatcherServlet.setEnableLoggingRequestDetails(webMvcProperties.isLogRequestDetails());
        return dispatcherServlet;
    }

}
