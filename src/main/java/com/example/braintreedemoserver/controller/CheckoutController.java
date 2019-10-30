package com.example.braintreedemoserver.controller;


import com.braintreegateway.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment/gateway")
@Slf4j
public class CheckoutController {


    @Autowired
    private BraintreeGateway braintreeGateway;

    private final String testUuid = UUID.randomUUID().toString();


    @PostMapping("/checkout")
    public Result checkout(@RequestBody String paymentNonce) {
        createTestCustomer();

        log.info("Executing Transaction with nonce: " + paymentNonce);
        TransactionRequest paymentRequest = new TransactionRequest()
                .customerId(testUuid)
                .amount(BigDecimal.TEN)
                .paymentMethodNonce(paymentNonce)
                .options()
                .submitForSettlement(true)
                .done();


        Result<Transaction> transRes = braintreeGateway.transaction().sale(paymentRequest);
        log.info(transRes.getMessage());

        return transRes;
    }


    private void createTestCustomer() {
        log.info("Create customer with id: " + testUuid);
        CustomerRequest customerRequest = new CustomerRequest()
                .id(testUuid)
                .firstName("Martin")
                .lastName("Mojko");
        Result<Customer> result = braintreeGateway.customer().create(customerRequest);
        log.info(result.getMessage());
    }

}
