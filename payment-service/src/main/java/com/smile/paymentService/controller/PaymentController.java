package com.smile.paymentService.controller;

import com.smile.paymentService.model.PaymentRequest;
import com.smile.paymentService.model.PaymentResponse;
import com.smile.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {


    @Autowired
    PaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<Long> doPayment(@RequestBody  PaymentRequest paymentRequest){
        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest)
                ,HttpStatus.OK);
    }


    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsById(@PathVariable String orderId){
         return new ResponseEntity<>(
                 paymentService.getPaymentDetailsById(orderId),
                 HttpStatus.OK
         );
    }
}
