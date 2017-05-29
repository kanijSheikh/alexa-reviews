package com.sapient.controller;

import com.sapient.domain.ProductDto;
import com.sapient.mapper.ProductMapper;
import com.sapient.model.Product;
import com.sapient.service.ProductService;
import com.sapient.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProductControllerRest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    ProductValidator productValidator;

    public static final String PRODUCT = "/products";
    public static final String PRODUCT_BY_ID = "/product/{id}";

    @RequestMapping(value = PRODUCT, method = POST,
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity addProducts(@RequestBody Collection<ProductDto> productDto) {
        productValidator.validate(productDto);
        List<Product> products = productDto.stream().map(productMapper::mapToDomain).collect(toList());
        productService.add(products);
        return new ResponseEntity(CREATED);
    }


    @RequestMapping(value = PRODUCT, method = GET,
            produces = {"application/json"})
    public Collection<ProductDto> getProducts(@RequestParam(value = "type", required = false) String productType,
                                              @RequestParam(value = "id", required = false) Integer productId) {
        Collection<Product> products = productService.get(productType, productId);
        productValidator.checkForEntityNotFound(products);
        Collection<ProductDto> productDtos = productMapper.mapToDto(products);
        return productDtos;
    }


    @RequestMapping(value = PRODUCT_BY_ID, method = DELETE,
            produces = {"application/json"})
    public ResponseEntity removeProduct(@PathVariable("id") Integer productId) {
        productService.remove(productId);
        return new ResponseEntity(NO_CONTENT);
    }

}
