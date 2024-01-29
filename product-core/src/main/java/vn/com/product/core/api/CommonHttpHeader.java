package vn.com.product.core.api;

import lombok.Getter;
import lombok.Setter;

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

    private String correlationId;
    private String userId;
    private String refId;
    private String locale;

    public Map<String, String> getHeaderMap() {
        return Map.of(CORRELATION_ID, this.correlationId,
                USER_ID, this.userId,
                REF_ID, this.refId,
                LANGUAGE, this.locale);
    }
}
