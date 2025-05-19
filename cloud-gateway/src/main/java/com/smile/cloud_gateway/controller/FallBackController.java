package com.smile.cloud_gateway.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/orderServiceFallback")
    public ResponseEntity<String> orderServiceFallback() {
        return ResponseEntity.ok("Order Service is temporarily unavailable.");
    }

    @GetMapping("/productServiceFallback")
    public ResponseEntity<String> productServiceFallback() {
        return ResponseEntity.ok("Product Service is temporarily unavailable.");
    }

    @GetMapping("/paymentServiceFallback")
    public ResponseEntity<String> paymentServiceFallback() {
        return ResponseEntity.ok("Payment Service is temporarily unavailable.");
    }
}
