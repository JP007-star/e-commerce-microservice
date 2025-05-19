package com.smile.orderService.service;

import com.smile.orderService.entity.Order;
import com.smile.orderService.exception.CustomException;
import com.smile.orderService.external.client.PaymentService;
import com.smile.orderService.external.client.ProductService;
import com.smile.orderService.external.request.PaymentRequest;
import com.smile.orderService.model.OrderRequest;
import com.smile.orderService.model.OrderResponse;
import com.smile.orderService.model.PaymentResponse;
import com.smile.orderService.model.ProductResponse;
import com.smile.orderService.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PaymentService paymentService;


    @Autowired
    RestTemplate restTemplate;

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

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Order details for order Id :: {}",orderId);

        Order order=orderRepository.findById(orderId)
                .orElseThrow(()->new CustomException("Order Details not found for the id :"+orderId,
                        "ORDER_NOT_FOUND",
                        404));


        log.info("Invoking product details for order Id :: {}",order.getProductId());

        ProductResponse productResponse=restTemplate.getForObject(
                "http://product-service/product/"+order.getProductId(),
                ProductResponse.class
        );

        log.info("Getting Payment Information from payment service ");

        PaymentResponse paymentResponse=restTemplate.getForObject(
                "http://payment-service/payment/order/" +order.getId(),
                PaymentResponse.class
        );

        OrderResponse.ProductDetails productDetails= OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .price(productResponse.getPrice())
                .productId(productResponse.getProductId())
                .build();

        OrderResponse.PaymentDetails paymentDetails= OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .orderId(paymentResponse.getOrderId())
                .amount(paymentResponse.getAmount())
                .status(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .build();

        OrderResponse orderResponse=OrderResponse.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .amount(order.getAmount()).build();
        return orderResponse;
    }
}
