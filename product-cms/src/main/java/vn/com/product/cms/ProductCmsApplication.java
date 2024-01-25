package vn.com.product.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableFeignClients
@ComponentScan("vn.com.product")
public class ProductCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductCmsApplication.class, args);
    }

}
