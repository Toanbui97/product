package vn.com.product.core.factory;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Getter
@Setter
public class BaseRequest<T> implements Serializable {

    private Header header;
    private transient T parameters;

    @Getter
    @Setter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Header implements Serializable {
        String[] roles;
    }
}
