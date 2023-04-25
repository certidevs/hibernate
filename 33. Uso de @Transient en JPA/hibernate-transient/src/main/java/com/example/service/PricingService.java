package com.example.service;

import com.example.model.Product;

public class PricingService {

    public static final double IVA = 1.21;

    public PricingService() {
    }

    public Product calculatePriceWithTaxes(Product product) {
        Double priceWithTaxes = product.getPrice() * IVA;
        return new Product(product.getName(), product.getPrice(), priceWithTaxes);
    }

}
