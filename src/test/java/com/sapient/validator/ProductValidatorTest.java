package com.sapient.validator;

import com.sapient.domain.ProductDto;
import com.sapient.exception.BadRequestException;
import com.sapient.exception.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import static java.util.Arrays.asList;

@RunWith(MockitoJUnitRunner.class)
public class ProductValidatorTest {
    private static final String PRODUCT_NAME = new Random().toString();
    private static final String PRODUCT_TYPE = new Random().toString();
    private static final String PRODUCT_PRICE = new Random().toString();
    private static final Integer PRODUCT_ID = new Random().nextInt();

    @InjectMocks
    private ProductValidator productValidator;

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionWhenProductDtoisEmpty() {

        productValidator.validate(asList());
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionWhenProductNameIsNull() {

        productValidator.validate(asList(new ProductDto(null, PRODUCT_TYPE, PRODUCT_PRICE)));
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionWhenProductNameIsEmpty() {

        productValidator.validate(asList(new ProductDto("", PRODUCT_TYPE, PRODUCT_PRICE)));
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionWhenProductTypeIsNull() {

        productValidator.validate(asList(new ProductDto(PRODUCT_NAME, null, PRODUCT_PRICE)));
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionWhenProductTypeIsEmpty() {

        productValidator.validate(asList(new ProductDto(PRODUCT_NAME, "", PRODUCT_PRICE)));
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenProductCollectionIsEmpty() {

        productValidator.checkForEntityNotFound(asList());
    }
}