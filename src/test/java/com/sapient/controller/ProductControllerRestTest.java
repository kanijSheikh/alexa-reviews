package com.sapient.controller;

import com.sapient.domain.ProductDto;
import com.sapient.mapper.ProductMapper;
import com.sapient.model.Product;
import com.sapient.model.ProductType;
import com.sapient.service.ProductService;
import com.sapient.validator.ProductValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerRestTest {
    private static final String PRODUCT_TYPE = "Car";
    private static final int PRODUCT_ID = 1;
    @InjectMocks
    private ProductControllerRest productControllerRest;
    @Mock
    private ProductValidator productValidator;
    @Mock
    private ProductService productService;
    @Mock
    private ProductMapper productMapper;

    @Test
    public void shouldAddProducts() {
        List<ProductDto> productDtos = asList(buildProductDto("Ferari", "10000$", "Car"));
        Product product = buildProductDomain();
        when(productMapper.mapToDomain(productDtos.get(0))).thenReturn(product);

        ResponseEntity responseEntity = productControllerRest.addProducts(productDtos);

        verify(productValidator).validate(productDtos);
        verify(productMapper).mapToDomain(productDtos.get(0));
        verify(productService).add(asList(product));
        assertEquals(CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void shouldGetProducts() {
        List<Product> productList = asList(buildProductDomain());
        when(productService.get(PRODUCT_TYPE, PRODUCT_ID)).thenReturn(productList);
        List<ProductDto> expectedDto = asList(buildProductDto("Ferari", "10000$", "Car"));
        when(productMapper.mapToDto(productList)).thenReturn(expectedDto);

        Collection<ProductDto> actualProductDto = productControllerRest.getProducts(PRODUCT_TYPE, PRODUCT_ID);

        verify(productService).get(PRODUCT_TYPE, PRODUCT_ID);
        verify(productValidator).checkForEntityNotFound(productList);
        assertEquals(expectedDto, actualProductDto);
    }

    @Test
    public void shouldDeleteProduct() {

        ResponseEntity responseEntity = productControllerRest.removeProduct(PRODUCT_ID);

        verify(productService).remove(PRODUCT_ID);
        assertEquals(NO_CONTENT, responseEntity.getStatusCode());

    }


    private Product buildProductDomain() {
        Product product = new Product();
        product.setName("Ferari");
        product.setName("1000$");
        product.setProductType(getProductType());
        return product;
    }

    private ProductType getProductType() {
        ProductType productType = new ProductType();
        productType.setType("Car");
        return productType;
    }


    private ProductDto buildProductDto(String name, String price, String type) {
        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setType(type);
        return productDto;
    }
}