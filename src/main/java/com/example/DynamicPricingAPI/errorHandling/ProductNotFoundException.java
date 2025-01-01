package com.example.DynamicPricingAPI.errorHandling;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message){
        super(message);
    }
}
