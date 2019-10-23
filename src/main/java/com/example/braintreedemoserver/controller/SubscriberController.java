package com.example.braintreedemoserver.controller;


import com.braintreegateway.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment/gateway")
@Slf4j
public class SubscriberController {

    @Autowired
    private BraintreeGateway braintreeGateway;


    private static String testSubsPlan = "monthly_subscriber";


    @PostMapping("/subscribe")
    public Result checkout(@RequestBody String paymentNonce) {
        log.info("Executing Subs Transaction with nonce: " + paymentNonce);

        CustomerRequest request = new CustomerRequest()
                .firstName("Test")
                .lastName("User")
                .paymentMethodNonce(paymentNonce);

        Result<Customer> customerRes = braintreeGateway.customer().create(request);
        log.info("LOG: " + customerRes.getMessage());

        PaymentMethod paymentMethod = customerRes.getTarget().getDefaultPaymentMethod();

        SubscriptionRequest subsRequest = new SubscriptionRequest()
                .paymentMethodToken(paymentMethod.getToken())
                .planId(testSubsPlan);

        Result<Subscription> subsRes = braintreeGateway.subscription().create(subsRequest);
        log.info("LOG: " + subsRes);

        return subsRes;
    }


}
