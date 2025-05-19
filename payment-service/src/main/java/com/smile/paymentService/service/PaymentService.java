package com.smile.paymentService.service;

import com.smile.paymentService.model.PaymentRequest;
import com.smile.paymentService.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsById(String orderId);
}
