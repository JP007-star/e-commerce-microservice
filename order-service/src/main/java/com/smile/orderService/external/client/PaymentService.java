package com.smile.orderService.external.client;


import com.smile.orderService.exception.CustomException;
import com.smile.orderService.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@CircuitBreaker(name = "external",fallbackMethod = "fallback")
@FeignClient("payment-service/payment")
public interface PaymentService {

    @PostMapping("/")
    public ResponseEntity<Long> doPayment(PaymentRequest paymentRequest);


    default void fallback(Exception ex){
        throw new CustomException(
                "Payment Service is down",
                "UNAVAILABLE",
                500
        );
    }
}
