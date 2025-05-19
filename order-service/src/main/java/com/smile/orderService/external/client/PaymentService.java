package com.smile.orderService.external.client;


import com.smile.orderService.external.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("payment-service/payment")
public interface PaymentService {

    @PostMapping("/")
    public ResponseEntity<Long> doPayment(PaymentRequest paymentRequest);
}
