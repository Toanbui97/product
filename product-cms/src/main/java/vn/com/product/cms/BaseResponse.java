package vn.com.product.cms;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse<T> implements Serializable {

    String code;
    String message;
    String errorMessage;
    T data;
}
