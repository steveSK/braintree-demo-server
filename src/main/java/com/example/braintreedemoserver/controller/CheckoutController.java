package com.example.braintreedemoserver.controller;


import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        TransactionRequest request = new TransactionRequest()
                .amount(BigDecimal.TEN)
                .paymentMethodNonce(paymentNonce)
                .options()
                .submitForSettlement(true)
                .done();

        return braintreeGateway.transaction().sale(request);

     /*   if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        } */
    }

}
