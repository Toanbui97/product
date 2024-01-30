package vn.com.product.core.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class CommonHttpHeader implements Serializable {

    public static final String CORRELATION_ID = "x-correlation-id";
    public static final String USER_ID = "x-user-id";
    public static final String REF_ID = "x-ref-id";
    public static final String LANGUAGE = "accept-language";
    public static final String LANGUAGE_DEFAULT = "en";
    public static final Set<String> COMMON_HEADER = Set.of(CORRELATION_ID, USER_ID, REF_ID, LANGUAGE);
    public static final Set<String> REQUIRED_HEADER = Set.of(USER_ID, REF_ID);

    private String correlationId;
    private String userId;
    private String refId;
    private String locale;

    public Map<String, String> getHeaderMap() {
        return Map.of(CORRELATION_ID, StringUtils.hasText(this.correlationId) ? this.correlationId : "",
                USER_ID, StringUtils.hasText(this.userId) ? this.userId : "",
                REF_ID, StringUtils.hasText(this.refId) ? this.refId : "",
                LANGUAGE, StringUtils.hasText(this.locale) ? this.locale : "");
    }
}
