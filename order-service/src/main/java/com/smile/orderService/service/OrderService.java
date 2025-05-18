package com.smile.orderService.service;

import com.smile.orderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
