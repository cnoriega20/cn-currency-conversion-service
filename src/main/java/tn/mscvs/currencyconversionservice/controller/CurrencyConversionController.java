package tn.mscvs.currencyconversionservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tn.mscvs.currencyconversionservice.domain.CurrencyConversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {
    private RestTemplate restTemplate;

    private final String URI = "http://localhost:8000/currency-exchange/get?from={from}&to={to}";

    @GetMapping("/convert-currency/get")
    public CurrencyConversion convert(@RequestParam("from") String from,
                                      @RequestParam("to") String to,
                                      @RequestParam("amount")BigDecimal amount){

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity(URI, CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        return CurrencyConversion.builder()
                .id(currencyConversion.getId())
                .from(from)
                .to(to)
                .conversionValue(currencyConversion.getConversionValue())
                .amount(amount)
                .totalAmount(amount.multiply(currencyConversion.getConversionValue()))
                .port(currencyConversion.getPort())
                .build();
    }

}
