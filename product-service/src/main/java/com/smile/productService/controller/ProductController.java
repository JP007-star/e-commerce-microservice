package com.smile.productService.controller;

import com.smile.productService.model.ProductRequest;
import com.smile.productService.model.ProductResponse;
import com.smile.productService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping("/")
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
        long productId=productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        List<ProductResponse> productResponse=productService.getProductAll();
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id){
        ProductResponse productResponse=productService.getProductById(id);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
}
