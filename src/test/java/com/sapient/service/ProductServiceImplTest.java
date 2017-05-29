package com.sapient.service;

import com.sapient.model.Product;
import com.sapient.model.ProductType;
import com.sapient.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
    private static final String PRODUCT_TYPE = "Car";
    private static final int PRODUCT_ID = 1;

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;

    @Test
    public void shouldAddProduct() {
        List<Product> products = asList();

        productService.add(products);

        verify(productRepository).add(products);
    }


    @Test
    public void shouldGetProductWhenProductTypeAndProductIdIsNotNull() {
        Product expectedProduct = buildProductDomain();
        when(productRepository.getProductBy(PRODUCT_TYPE, PRODUCT_ID)).thenReturn(expectedProduct);

        Collection<Product> actualProduct = productService.get(PRODUCT_TYPE, PRODUCT_ID);

        verify(productRepository).getProductBy(PRODUCT_TYPE, PRODUCT_ID);
        assertEquals(asList(expectedProduct), actualProduct);
    }

    @Test
    public void shouldGetProductWhenProductTypeIsNotNullAndProductIdIsNull() {
        Product expectedProduct = buildProductDomain();
        when(productRepository.getProductsByType(PRODUCT_TYPE)).thenReturn(asList(expectedProduct));

        Collection<Product> actualProduct = productService.get(PRODUCT_TYPE, null);

        verify(productRepository).getProductsByType(PRODUCT_TYPE);
        assertEquals(asList(expectedProduct), actualProduct);
    }

    @Test
    public void shouldGetProductWhenProductTypeIsNullAndProductIdIsNotNull() {
        Product expectedProduct = buildProductDomain();
        when(productRepository.getProductById(PRODUCT_ID)).thenReturn(expectedProduct);

        Collection<Product> actualProduct = productService.get(null, PRODUCT_ID);

        verify(productRepository).getProductById(PRODUCT_ID);
        assertEquals(asList(expectedProduct), actualProduct);
    }

    @Test
    public void shouldGetProductWhenProductTypeAndProductIdIsNull() {
        Product expectedProduct = buildProductDomain();
        when(productRepository.getAllProducts()).thenReturn(asList(expectedProduct));

        Collection<Product> actualProduct = productService.get(null, null);

        verify(productRepository).getAllProducts();
        assertEquals(asList(expectedProduct), actualProduct);
    }

    @Test
    public void shouldDeleteTheProduct() {

        productService.remove(PRODUCT_ID);

        verify(productRepository).remove(PRODUCT_ID);
    }

    private Product buildProductDomain() {
        Product product = new Product();
        product.setName("Ferari");
        product.setPrice("10000$");
        ProductType productType = new ProductType();
        productType.setType("Car");
        product.setProductType(productType);
        return product;
    }
}