package tn.mscvs.currencyconversionservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.mscvs.currencyconversionservice.domain.CurrencyConversion;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    @GetMapping("/convert-currency/get")
    public CurrencyConversion convert(@RequestParam("from") String from,
                                      @RequestParam("to") String to,
                                      @RequestParam("amount")BigDecimal amount){
        return CurrencyConversion.builder()
                .id(1L)
                .from(from)
                .to(to)
                .conversionValue(BigDecimal.TEN)
                .amount(amount)
                .totalAmount(amount)
                .port(0)
                .build();
    }

}
