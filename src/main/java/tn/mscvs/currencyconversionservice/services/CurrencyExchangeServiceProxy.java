package tn.mscvs.currencyconversionservice.services;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.mscvs.currencyconversionservice.domain.Exchange;

//@FeignClient(name = "cn-currency-exchange-service", url = "http://localhost:8000") 1st change
//@FeignClient(name = "cn-currency-exchange-service") 2nd change
@FeignClient(name = "cn-zuul-api-gateway-server")
@RibbonClient(name = "cn-currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

    //@GetMapping("/currency-exchange/get")
    @GetMapping("/cn-currency-exchange-service/currency-exchange/get")
    public Exchange getExchangeRate(@RequestParam("from") String from,
                                    @RequestParam("to") String to);
}
