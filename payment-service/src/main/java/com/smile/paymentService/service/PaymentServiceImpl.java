package com.smile.paymentService.service;

import com.smile.paymentService.entity.TransactionalDetails;
import com.smile.paymentService.model.PaymentRequest;
import com.smile.paymentService.repository.TransactionalDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentServiceImpl implements PaymentService{

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
    @Autowired
    TransactionalDetailsRepository transactionalDetailsRepository;


    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Do payment :: {}",paymentRequest);

        TransactionalDetails transactionalDetails=TransactionalDetails.builder()
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentDate(Instant.now())
                .paymentStatus("SUCCESS")
                .amount(paymentRequest.getAmount())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .build();

        transactionalDetailsRepository.save(transactionalDetails);

        log.info("Transactional Details Completed with Id ..! ::{}",transactionalDetails.getId());
        return transactionalDetails.getId();
    }
}
