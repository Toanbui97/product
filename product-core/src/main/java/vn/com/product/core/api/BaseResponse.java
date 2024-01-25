package vn.com.product.core.api;

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
    private String errorKey;
    private String message;
    private String traceId;
    private String fieldError;
    private long responseTimestamp = Instant.now().toEpochMilli();
    private Integer page;
    private Integer size;
    private Long totalRecord;
    private Long totalPage;
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
    public BaseResponse(int status, String message, String errorKey, String traceId, String fieldError) {
        this(status, message, traceId);
        this.errorKey = errorKey;
        this.fieldError = fieldError;
    }

    public BaseResponse(int status, String message, String errorKey, String traceId, String fieldError, T data) {
       this(status, message, errorKey, traceId, fieldError);
       this.data = data;
    }

}
