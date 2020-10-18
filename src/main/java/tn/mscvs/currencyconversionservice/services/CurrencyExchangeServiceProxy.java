package tn.mscvs.currencyconversionservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.mscvs.currencyconversionservice.domain.Exchange;

@FeignClient(name = "cn-currency-exchange-service", url = "http://localhost:8000")
public interface CurrencyExchangeServiceProxy {

    @GetMapping("/currency-exchange/get")
    public Exchange getExchangeRate(@RequestParam("from") String from,
                                    @RequestParam("to") String to);
}
