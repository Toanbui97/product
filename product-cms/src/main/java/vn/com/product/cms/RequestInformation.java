package vn.com.product.cms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class RequestInformation implements Serializable {


    public RequestInformation(String requestId, String ref, String userId) {
        this.requestId = requestId;
        this.ref = ref;
        this.userId = userId;
    }

    public RequestInformation() {
    }

    private String requestId;
    private String ref;
    private String userId;
}
