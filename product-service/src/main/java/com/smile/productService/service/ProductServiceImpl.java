package com.smile.productService.service;


import com.smile.productService.entity.Product;
import com.smile.productService.exception.ProductServiceCustomException;
import com.smile.productService.model.ProductRequest;
import com.smile.productService.model.ProductResponse;
import com.smile.productService.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product...");
        Product product=Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product Created..");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(String id) {

        log.info(" getting product by id : {}",id);
        Product product=productRepository.findById(Long.valueOf(id)).orElseThrow(()->
            new ProductServiceCustomException("product not found exception","PRODUCT_NOT_FOUND")
        );

        ProductResponse productResponse=new ProductResponse();
        BeanUtils.copyProperties(product,productResponse);
        return productResponse;
    }

    @Override
    public List<ProductResponse> getProductAll() {
        log.info("Getting all products");

        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponses = products.stream()
                .map(product -> {
                    ProductResponse response = new ProductResponse();
                    BeanUtils.copyProperties(product, response);
                    return response;
                })
                .collect(Collectors.toList());

        return productResponses;
    }

}
