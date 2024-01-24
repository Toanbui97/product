package vn.com.product.cms;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class WebClientException extends Exception {

    private final HttpStatusCode httpStatusCode;
    private final String code;
    private final String requestId;
    private final String message;

    public WebClientException(HttpStatusCode httpStatusCode, String code, String requestId, String message) {
        this.httpStatusCode = httpStatusCode;
        this.requestId = requestId;
        this.code = code;
        this.message = message;
    }
}
