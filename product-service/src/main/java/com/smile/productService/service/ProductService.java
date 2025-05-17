package com.smile.productService.service;

import com.smile.productService.model.ProductRequest;
import com.smile.productService.model.ProductResponse;

import java.util.List;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(String id);

    List<ProductResponse> getProductAll();
}
