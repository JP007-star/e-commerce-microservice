package com.smile.orderService.service;

import com.smile.orderService.entity.Order;
import com.smile.orderService.external.client.ProductService;
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

        log.info("Order placed  successfully with order Id :: {}",order.getId());

        return order.getId();
    }
}
