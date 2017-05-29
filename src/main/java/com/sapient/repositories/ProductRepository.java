package com.sapient.repositories;

import com.sapient.model.Product;

import java.util.Collection;

public interface ProductRepository {
    void add(Collection<Product> products);

    Collection<Product> getProductsByType(String productType);

    Product getProductById(Integer productId);

    Product getProductBy(String productType, Integer productId);

    Collection<Product> getAllProducts();


    void remove(Integer productId);
}
