package vn.com.product.core.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class DateTimeUtils {

    public static Long getCurrentTimestamp() {
        return Instant.now().toEpochMilli();
    }
}
