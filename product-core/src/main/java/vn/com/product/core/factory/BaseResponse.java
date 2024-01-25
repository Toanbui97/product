package vn.com.product.core.factory;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> implements Serializable {

    private int status;
    private String message;
    private String errorMessage;
    private String traceId;
    private Long responseTimestamp = Instant.now().toEpochMilli();
    private Integer page;
    private Integer size;
    private Long totalRecord;
    private Integer totalPage;
    private transient T data;

    public BaseResponse(int status, String message, String traceId) {
        this.status = status;
        this.message = message;
        this.traceId = traceId;
    }

    public BaseResponse(int status, String message, String traceId, T data) {
        this(status, message, traceId);
        this.data = data;
    }
}
