package vn.com.product.core.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPatternParser;
import vn.com.product.core.api.CommonHttpHeader;
import vn.com.product.core.api.ResponseFactory;
import vn.com.product.core.constant.ExceptionMessage;
import vn.com.product.core.constant.FilterOrderConstant;
import vn.com.product.core.exception.BaseException;
import vn.com.product.core.exception.MissingRequireHeaderException;
import vn.com.product.core.utils.JSONUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Order(value = FilterOrderConstant.HTTP_HEADER_REQUIRE_FILTER)
public class HttpHeaderRequireFilter extends OncePerRequestFilter {

    @Value("${endpoint.no-auth}")
    private String[] noAuthPaths;

    private final ResponseFactory responseFactory;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response
            ,@NonNull FilterChain filterChain) throws ServletException, IOException {

        var uri = request.getRequestURI();
        var isNoAuthEndpoint = Arrays.stream(noAuthPaths).anyMatch(path ->
                PathPatternParser.defaultInstance.parse(path)
                        .matches(PathContainer.parsePath(uri)));
        try {
            if (!isNoAuthEndpoint) {
                for (var header : CommonHttpHeader.COMMON_HEADER) {
                    if (!StringUtils.hasText(request.getHeader(header)))
                        throw new MissingRequireHeaderException(ExceptionMessage.EXCEPT0002, header);
                }
            }
            filterChain.doFilter(request, response);
        } catch (BaseException exception) {
            handleFilterException(response, exception);
        }
    }

    private void handleFilterException(HttpServletResponse response, BaseException throwable) throws IOException {
        response.getWriter().write(Objects.requireNonNull(JSONUtils.writeValueAsString(
                responseFactory.fail(null, HttpStatusCode.valueOf(400), throwable)
                        .getBody())));
        response.setStatus(400);
        response.setContentType("application/json");
        response.getWriter().flush();
    }
}
