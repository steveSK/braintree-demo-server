package com.example.braintreedemoserver.controller;


import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
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

    private static String testSubsPlan = "monthly_subscriber";


    @PostMapping("/checkout")
    public Result checkout(@RequestBody String paymentNonce) {
        log.info("Executing Transaction with nonce: " + paymentNonce);
        TransactionRequest paymentRequest = new TransactionRequest()
                .amount(BigDecimal.TEN)
                .paymentMethodNonce(paymentNonce)
                .options()
                .submitForSettlement(true)
                .done();


        Result<Transaction> transRes = braintreeGateway.transaction().sale(paymentRequest);
        log.info(transRes.getMessage());

        return transRes;
    }

}
