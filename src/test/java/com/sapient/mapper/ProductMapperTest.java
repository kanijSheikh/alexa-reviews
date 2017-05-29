package com.sapient.mapper;

import com.sapient.domain.ProductDto;
import com.sapient.model.Product;
import com.sapient.model.ProductType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductMapperTest {

    private static final String PRODUCT_NAME = new Random().toString();
    private static final String PRODUCT_TYPE = new Random().toString();
    private static final String PRODUCT_PRICE = new Random().toString();
    private static final Integer PRODUCT_ID = new Random().nextInt();
    @InjectMocks
    private ProductMapper productMapper;

    @Test
    public void shouldMapToDomain() {
        ProductDto productDto = new ProductDto(PRODUCT_NAME, PRODUCT_TYPE, PRODUCT_PRICE);

        Product product = productMapper.mapToDomain(productDto);

        assertEquals(PRODUCT_NAME, product.getName());
        assertEquals(PRODUCT_TYPE, product.getProductType().getType());
        assertEquals(PRODUCT_PRICE, product.getPrice());
    }

    @Test
    public void shouldMapToDto() {
        Product product = buildProductDomain();

        Collection<ProductDto> productDtos = productMapper.mapToDto(Arrays.asList(product));

        for (ProductDto productDto : productDtos) {
            assertEquals(PRODUCT_ID, productDto.getId());
            assertEquals(PRODUCT_NAME, productDto.getName());
            assertEquals(PRODUCT_TYPE, productDto.getType());
            assertEquals(PRODUCT_PRICE, productDto.getPrice());
        }
    }

    private Product buildProductDomain() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        product.setName(PRODUCT_NAME);
        product.setPrice(PRODUCT_PRICE);
        ProductType productType = new ProductType();
        productType.setType(PRODUCT_TYPE);
        product.setProductType(productType);
        return product;
    }
}