package com.example.braintreedemoserver.config;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BraintreeAppConfig {

    @Value("${braintree.sandbox.merchant.id}")
    private String merchantId;

    @Value("${braintree.sandbox.public.key}")
    private String publicKey;

    @Value("${braintree.sandbox.private.key}")
    private String privateKey;


    @Bean
    public BraintreeGateway braintreeGateway() {
       return new BraintreeGateway(Environment.SANDBOX, merchantId, publicKey, privateKey);
    }

}
