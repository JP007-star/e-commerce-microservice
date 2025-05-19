package com.smile.orderService.external.client;


import com.smile.orderService.exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CircuitBreaker(name = "external",fallbackMethod = "fallback")
@FeignClient(name = "product-service/product")
public interface ProductService {

    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(
            @PathVariable long id,
            @RequestParam long quantity
    );

    default void fallback(Exception ex){
        throw new CustomException(
                "Product Service is down",
                "UNAVAILABLE",
                500
        );
    }
}
