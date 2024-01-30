package vn.com.product.core.log;

import feign.Util;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.product.core.api.CommonHttpHeader;
import vn.com.product.core.constant.FilterOrderConstant;
import vn.com.product.core.utils.HttpRequestWrapper;

import java.io.IOException;
import java.util.UUID;

@Order(value = FilterOrderConstant.HTTP_REQUEST_LOGGING_FILTER)
@Slf4j
@Component
@RequiredArgsConstructor
public class HttpRequestLogger extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response
            , @NonNull FilterChain filterChain) throws ServletException, IOException {

        var headerLogString = new StringBuilder();
        CommonHttpHeader.COMMON_HEADER.forEach(key -> {
            var value = request.getHeader(key);
            if (StringUtils.hasText(value)) {
                headerLogString.append(StringTemplate.STR."\{ key }= \{ value }")
                        .append(System.lineSeparator());
            }
        });

        var requestCached = new HttpRequestWrapper(request);
        var bodyData = requestCached.getCache();
        if (bodyData.length > 0) {
            log.info("""

                            ---> Receive: {}.{}
                            {}
                            {}
                            """,
                    request.getMethod(), request.getRequestURL(),
                    headerLogString,
                    Util.decodeOrDefault(bodyData, Util.UTF_8, "Binary data"));
        } else {
            log.info("""
                                                        
                            ---> Receive: {}.{}
                            {}
                            """,
                    request.getMethod(), request.getRequestURL(),
                    headerLogString);
        }

        filterChain.doFilter(requestCached, response);
    }
}
