package tn.mscvs.currencyconversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class CnCurrencyConversionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CnCurrencyConversionServiceApplication.class, args);
    }

}
