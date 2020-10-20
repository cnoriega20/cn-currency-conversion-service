package tn.mscvs.currencyconversionservice.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tn.mscvs.currencyconversionservice.domain.CurrencyConversion;
import tn.mscvs.currencyconversionservice.domain.Exchange;
import tn.mscvs.currencyconversionservice.services.CurrencyExchangeServiceProxy;

import java.math.BigDecimal;
import java.util.Collections;

@RestController
public class CurrencyConversionController {

    private RestTemplate restTemplate;


    private final CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    //private final String URI = "http://localhost:8000/currency-exchange/get?from={from}&to={to}";
    private final String URI = "http://localhost:8000/currency-exchange/get";

    public CurrencyConversionController(CurrencyExchangeServiceProxy currencyExchangeServiceProxy) {
        this.currencyExchangeServiceProxy = currencyExchangeServiceProxy;
    }

    @GetMapping("/convert-currency/get")
    public CurrencyConversion convert(@RequestParam("from") String from,
                                      @RequestParam("to") String to,
                                      @RequestParam("amount")BigDecimal amount){

        restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        HttpEntity<String> requestEntity = new HttpEntity(requestHeaders);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(URI)
                .queryParam("from", from)
                .queryParam("to", to);
        ResponseEntity<Exchange> responseEntity = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                Exchange.class );

        CurrencyConversion resultConversion = CurrencyConversion.builder()
                .id(responseEntity.getBody().getId())
                .from(from)
                .to(to)
                .conversionValue(responseEntity.getBody().getConversion())
                .amount(amount)
                .totalAmount(amount.multiply(responseEntity.getBody().getConversion()))
                .port(responseEntity.getBody().getPort())
                .build();

        return resultConversion;
    }

    @GetMapping("/convert-currency-feign/get")
    public CurrencyConversion convertWithFeign(@RequestParam("from") String from,
                                               @RequestParam("to") String to,
                                               @RequestParam("amount")BigDecimal amount){

        Exchange exchangeResponse = currencyExchangeServiceProxy.getExchangeRate(from,to);

            CurrencyConversion resultConversion = CurrencyConversion.builder()
                    .id(exchangeResponse.getId())
                    .from(from)
                    .to(to)
                    .conversionValue(exchangeResponse.getConversion())
                    .amount(amount)
                    .totalAmount(amount.multiply(exchangeResponse.getConversion()))
                    .port(exchangeResponse.getPort())
                    .build();
        return resultConversion;
    }

}
