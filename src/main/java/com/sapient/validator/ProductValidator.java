package com.sapient.validator;

import com.sapient.domain.ProductDto;
import com.sapient.exception.BadRequestException;
import com.sapient.exception.EntityNotFoundException;
import com.sapient.model.Product;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Objects.isNull;

@Component
public class ProductValidator {

    public void validate(Collection<ProductDto> products) {
        validateProductDto(products);

        for (ProductDto product : products) {
            validateProductAttributes(product);
        }
    }

    private void checkProductName(Collection<ProductDto> productDto) {
        if (productDto.isEmpty()) {
            throw new BadRequestException();
        }
    }

    private void validateProductDto(Collection<ProductDto> productDto) {
        if (productDto.isEmpty()) {
            throw new BadRequestException();
        }
    }

    private void validateProductAttributes(ProductDto productDto) {
        String productName = productDto.getName();
        String productType = productDto.getType();

        if (isNull(productName) || productName.trim().isEmpty()) {
            throw new BadRequestException();
        }
        if (isNull(productType) || productType.trim().isEmpty()) {
            throw new BadRequestException();
        }
    }

    public void checkForEntityNotFound(Collection<Product> products) {
        if (products.isEmpty()) {
            throw new EntityNotFoundException();
        }

    }


}
