package com.example.braintreedemoserver.controller;


import com.braintreegateway.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment/gateway")
@Slf4j
public class CheckoutController {


    @Autowired
    private BraintreeGateway braintreeGateway;


    @PostMapping("/checkout")
    public Result<Transaction> checkout(@RequestBody String paymentNonce) {
        log.info("Executing Transaction with nonce: " + paymentNonce);
        TransactionRequest paymentRequest = new TransactionRequest()
                .amount(BigDecimal.TEN)
                .paymentMethodNonce(paymentNonce)
                .options()
                .submitForSettlement(true)
                .done();

        CustomerRequest request = new CustomerRequest()
                .firstName("Test")
                .lastName("User")
                .paymentMethodNonce(paymentNonce);

        Result<Customer> customerRes = braintreeGateway.customer().create(request);


     /*   PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest()
                .customerId("131866")
                .paymentMethodNonce(paymentNonce);

        Result<? extends PaymentMethod> paymentMethodResult = braintreeGateway.paymentMethod().
                create(paymentMethodRequest); */

        return braintreeGateway.transaction().sale(paymentRequest);

     /*   if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        } */
    }

}
