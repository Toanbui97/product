package vn.com.product.cms;

public class RequestInformation {


    public RequestInformation(String requestId, String ref, String userId) {
        this.requestId = requestId;
        this.ref = ref;
        this.userId = userId;
    }

    public RequestInformation() {
    }

    String requestId;
    String ref;
    String userId;
}
