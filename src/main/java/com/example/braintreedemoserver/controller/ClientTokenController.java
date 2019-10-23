package com.example.braintreedemoserver.controller;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/gateway")
@Slf4j
public class ClientTokenController {

    @Autowired
    private BraintreeGateway braintreeGateway;


    @GetMapping(value = "/client_token", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getClientToken() {

        String token = braintreeGateway.clientToken().generate();
        log.info("Sending token " + token);
        return token;
    }

}
