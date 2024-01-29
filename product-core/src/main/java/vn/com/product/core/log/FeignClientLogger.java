package vn.com.product.core.log;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.com.product.core.utils.JSONUtils;

import java.io.IOException;

@Component
@Slf4j
public class FeignClientLogger extends Logger {
    @Override
    @SuppressWarnings("squid:S3457")
    protected void log(String configKey, String format, Object... args) {
        log.info(String.format(methodTag(configKey) + format, args));
    }

    @Override
    protected void logRequest(String configKey, Logger.Level logLevel, Request request) {
        var logString = new StringBuilder(System.lineSeparator());
        String protocolVersion = resolveProtocolVersion(request.protocolVersion());

        logString.append(String.format(methodTag(configKey) + "---> %s %s %s", request.httpMethod().name(), request.url(), protocolVersion))
                .append(System.lineSeparator());

        request.headers().forEach((key, value) ->
                logString.append(StringTemplate.STR. "\{ key }= \{ String.join(",", value) }" )
                .append(System.lineSeparator()));

        if (request.body() != null) {
            var bodyString = request.charset() != null ? new String(request.body(), request.charset()) : "Binary Data";
            logString.append(JSONUtils.prettyPrintString(bodyString));
        }

        log.info(logString.toString());
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        var logString = new StringBuilder(System.lineSeparator());
        String protocolVersion = resolveProtocolVersion(response.protocolVersion());
        String reason = response.reason() != null ? " " + response.reason() : "";
        int status = response.status();

        logString.append(String.format(methodTag(configKey) + "<--- %s %s %s %s (%sms)"
                        , response.request().url(), protocolVersion, status, reason, elapsedTime))
                .append(System.lineSeparator());

        int bodyLength;
        if (response.body() != null && status != 204 && status != 205) {
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
            bodyLength = bodyData.length;
            if (bodyLength > 0) {
                logString.append(JSONUtils.prettyPrintString(
                        Util.decodeOrDefault(bodyData, Util.UTF_8, "Binary data")))
                        .append(System.lineSeparator());
            }
            log.info(logString.toString());
            return response.toBuilder().body(bodyData).build();
        } else {
            log.info(logString.toString());
            return response;
        }
    }
}
