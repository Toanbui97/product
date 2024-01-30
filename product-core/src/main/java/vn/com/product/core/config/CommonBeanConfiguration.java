package vn.com.product.core.config;

import io.swagger.v3.oas.models.parameters.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.DispatcherServlet;
import vn.com.product.core.api.CommonHttpHeader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class CommonBeanConfiguration {

    @Bean(name = "commonHttpHeader")
    @RequestScope
    public CommonHttpHeader commonHttpHeader(HttpServletRequest httpServletRequest) {
        var commonHeader = new CommonHttpHeader();
        commonHeader.setCorrelationId(httpServletRequest.getHeader(CommonHttpHeader.CORRELATION_ID));
        commonHeader.setRefId(httpServletRequest.getHeader(CommonHttpHeader.REF_ID));
        commonHeader.setUserId(httpServletRequest.getHeader(CommonHttpHeader.USER_ID));
        commonHeader.setLocale(httpServletRequest.getHeader(CommonHttpHeader.LANGUAGE));
        return commonHeader;
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newThreadPerTaskExecutor(Thread.ofVirtual()
                .inheritInheritableThreadLocals(true).factory());
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

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, _) -> {
            CommonHttpHeader.COMMON_HEADER.forEach(header ->
                    operation.addParametersItem(
                            new Parameter()
                                    .in("header")
                                    .name(header)
                                    .required(false)
                    ));
            return operation;
        };
    }

}
