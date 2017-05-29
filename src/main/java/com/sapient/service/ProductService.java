package com.sapient.service;

import com.sapient.model.Product;

import java.util.Collection;

public interface ProductService {
    void add(Collection<Product> products);

    Collection<Product> get(String productType, Integer productId);

    void remove(Integer productId);


}