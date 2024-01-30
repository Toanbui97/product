package vn.com.product.core.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.product.core.api.CommonHttpHeader;
import vn.com.product.core.constant.FilterOrderConstant;
import vn.com.product.core.utils.HttpRequestWrapper;

import java.io.IOException;
import java.util.UUID;

@Order(FilterOrderConstant.HTTP_HEADER_SETTING_FILTER)
@Component
public class DefaultHeaderSettingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response
            , @NonNull FilterChain filterChain) throws ServletException, IOException {

        var requestWrapper = new HttpRequestWrapper(request);
        if (!StringUtils.hasText(request.getHeader(CommonHttpHeader.CORRELATION_ID)))
            requestWrapper.setHeader(CommonHttpHeader.CORRELATION_ID, UUID.randomUUID().toString());
        if (!StringUtils.hasText(request.getHeader(CommonHttpHeader.LANGUAGE)))
            requestWrapper.setHeader(CommonHttpHeader.LANGUAGE, CommonHttpHeader.LANGUAGE_DEFAULT);

        filterChain.doFilter(requestWrapper, response);
    }
}
