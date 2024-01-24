package vn.com.product.cms;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class HttpRequestUtils {
    public static final Set<String> HEADER_NAME_SET = Set.of("trace-id", "ref", "access-token");
    public Map<String, Object> getRequestInfo(HttpServletRequest httpServletRequest) {
//        return Map.of("trace-id", httpServletRequest.getHeader("trace-id"),
//                "ref", httpServletRequest.getHeader("ref"),
//                "access-token", httpServletRequest.getHeader("access-token"));

//        return Collections.list(httpServletRequest.getHeaderNames())
//                .stream().collect(Collectors.toMap(Function.identity(), httpServletRequest::getHeader));

        return HEADER_NAME_SET.stream().collect(Collectors.toMap(Function.identity(), httpServletRequest::getHeader));
    }
}
