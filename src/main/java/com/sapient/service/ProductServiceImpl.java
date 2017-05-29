package com.sapient.service;

import com.sapient.model.Product;
import com.sapient.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void add(Collection<Product> products) {
        productRepository.add(products);
    }

    @Override
    public Collection<Product> get(String productType, Integer productId) {
        if (nonNull(productType) && nonNull(productId)) {
            return singletonList(productRepository.getProductBy(productType, productId));
        } else {
            if (nonNull(productId)) {
                return singletonList(productRepository.getProductById(productId));
            }
            if (nonNull(productType)) {
                return productRepository.getProductsByType(productType);
            } else {
                return productRepository.getAllProducts();
            }
        }
    }

    @Override
    public void remove(Integer productId) {
        productRepository.remove(productId);
    }
}
