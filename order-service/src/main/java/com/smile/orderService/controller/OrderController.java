package com.smile.orderService.controller;


import com.smile.orderService.model.OrderRequest;
import com.smile.orderService.model.OrderResponse;
import com.smile.orderService.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {


    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
       long orderId=orderService.placeOrder(orderRequest);
       log.info("Order Id : {}",orderId);

       return  new ResponseEntity<>(orderId, HttpStatus.OK);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId){
        OrderResponse orderResponse=orderService.getOrderDetails(orderId);
        return  new ResponseEntity<>(orderResponse,HttpStatus.OK);
    }

}
