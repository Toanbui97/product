package vn.com.product.cms;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ClientService {

    private final HttpServletRequest httpServletRequest;


}
