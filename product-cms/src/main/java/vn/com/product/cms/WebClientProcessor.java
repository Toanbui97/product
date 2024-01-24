package vn.com.product.cms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@Slf4j
public class WebClientProcessor {

    public static ExchangeFilterFunction webClientRequestProcessor() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Request: \n");
                //append clientRequest method and url
                clientRequest
                        .headers()
                        .forEach((headerName, values) -> values.forEach(value -> log.info("{}={}", headerName, value)));
                log.debug(sb.toString());
            }
            return Mono.just(clientRequest);
        });
    }

    public static ExchangeFilterFunction webClientResponseProcessor() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return clientResponse.bodyToMono(String.class)
                    .flatMap(s -> {
                        log.info(s);
                        return Mono.just(clientResponse);
                    });

        });
    }

    public static ExchangeFilterFunction webClientErrorProcessor() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return null;
            }
            return null;
        });
    }
}
