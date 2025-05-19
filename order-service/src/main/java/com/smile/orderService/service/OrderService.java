package com.smile.orderService.service;

import com.smile.orderService.model.OrderRequest;
import com.smile.orderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
