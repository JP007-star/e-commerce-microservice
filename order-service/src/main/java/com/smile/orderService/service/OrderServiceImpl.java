package com.smile.orderService.service;

import com.smile.orderService.entity.Order;
import com.smile.orderService.external.client.PaymentService;
import com.smile.orderService.external.client.ProductService;
import com.smile.orderService.external.request.PaymentRequest;
import com.smile.orderService.model.OrderRequest;
import com.smile.orderService.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PaymentService paymentService;

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        log.info("Placing order request :: {}",orderRequest);


        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Create order with status created ..");
        Order order= Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .orderStatus("Created")
                .productId(orderRequest.getProductId())
                .build();
        orderRepository.save(order);

        log.info("Calling payment service to complete the payment");

        PaymentRequest paymentRequest=PaymentRequest.builder()
                        .orderId(order.getId())
                        .paymentMode(orderRequest.getPaymentMode())
                        .amount(orderRequest.getTotalAmount())
                        .build();

        String orderStatus=null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment Done Successfully .. Status Changing to Placed");
            orderStatus="PLACED";
        }
        catch (Exception ex){
            log.info("Error occurred in payment .. Status Changing to Payment Failed :: {}",ex);
            orderStatus="PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order placed  successfully with order Id :: {}",order.getId());

        return order.getId();
    }
}
