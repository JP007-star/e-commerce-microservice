package com.smile.paymentService.service;

import com.smile.paymentService.model.PaymentRequest;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);
}
