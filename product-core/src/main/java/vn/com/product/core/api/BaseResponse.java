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
    private String correlationId;
    private String fieldError;
    private long responseTimestamp = Instant.now().toEpochMilli();
    private Integer page;
    private Integer size;
    private Long totalRecord;
    private Long totalPage;
    private transient T data;

    public BaseResponse(int status, String message, String correlationId) {
        this.status = status;
        this.message = message;
        this.correlationId = correlationId;
    }

    public BaseResponse(int status, String message, String correlationId, T data) {
        this(status, message, correlationId);
        this.data = data;
    }
    public BaseResponse(int status, String message, String errorKey, String correlationId, String fieldError) {
        this(status, message, correlationId);
        this.errorKey = errorKey;
        this.fieldError = fieldError;
    }

    public BaseResponse(int status, String message, String errorKey, String correlationId, String fieldError, T data) {
       this(status, message, errorKey, correlationId, fieldError);
       this.data = data;
    }

}
