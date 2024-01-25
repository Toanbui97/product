package vn.com.product.core.factory;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class CommonHttpHeader implements Serializable {

    public static final Set<String> COMMON_HEADER = Set.of("trace-id", "user-id", "ref", "locale");

    private String traceId;
    private String userId;
    private String ref;
    private String locale;

    public Map<String, String> getHeaderMap() {
        return Map.of("trace-id", this.traceId,
                "user-id", this.userId,
                "ref", this.ref,
                "locale", this.locale);
    }
}
