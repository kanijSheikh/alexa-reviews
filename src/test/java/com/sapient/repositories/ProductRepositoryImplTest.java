package com.sapient.repositories;

import com.sapient.model.Product;
import com.sapient.model.ProductType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ProductRepositoryImplTest {

    private static final String PRODUCT_TYPE = "Car";
    private static final int PRODUCT_ID = 1;
    private static final String EXPECTED_PRODUCT_TYPE_QUERY = "FROM Product AS product where product.productType.type = :productType";
    private static final String EXPECTED_PRODUCT_ID_QUERY = "FROM Product p where p.id =:productId";
    private static final String EXPECTED_PRODUCT_ID_TYPE_QUERY = "FROM Product p where p.productType.type =:productType and p.id =:productId";
    private static final String EXPECTED_ALL_PRODUCT_QUERY = "FROM Product AS p";

    @InjectMocks
    private ProductRepositoryImpl productRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery mockedQuery;

    @Test
    public void shouldPersistTheProducts() {
        Product product = new Product();

        productRepository.add(asList(product));

        verify(entityManager).persist(product);
    }

    @Test
    public void shouldGetProductsByType() {
        when(entityManager.createQuery(eq(EXPECTED_PRODUCT_TYPE_QUERY), eq(Product.class))).thenReturn(mockedQuery);
        List<Product> expectedProductList = asList(buildProductDomain("Ferari", "10000$", "Car"), buildProductDomain("Activa", "500$", "Mopet"));
        when(mockedQuery.getResultList()).thenReturn(expectedProductList);

        Collection<Product> actualProductList = productRepository.getProductsByType(PRODUCT_TYPE);

        verify(mockedQuery).setParameter("productType", PRODUCT_TYPE);
        verify(mockedQuery).getResultList();
        assertEquals(actualProductList, expectedProductList);

    }

    @Test
    public void shouldGetProductsById() {
        when(entityManager.createQuery(eq(EXPECTED_PRODUCT_ID_QUERY), eq(Product.class))).thenReturn(mockedQuery);
        Product expectedProduct = buildProductDomain("Ferari", "10000$", "Car");
        when(mockedQuery.getSingleResult()).thenReturn(expectedProduct);

        Product actualProduct = productRepository.getProductById(PRODUCT_ID);

        verify(mockedQuery).setParameter("productId", PRODUCT_ID);
        verify(mockedQuery).getSingleResult();
        assertEquals(actualProduct, expectedProduct);
    }

    @Test
    public void shouldGetProductsByIdAndName() {
        when(entityManager.createQuery(eq(EXPECTED_PRODUCT_ID_TYPE_QUERY), eq(Product.class))).thenReturn(mockedQuery);
        Product expectedProduct = buildProductDomain("Ferari", "10000$", "Car");
        when(mockedQuery.getSingleResult()).thenReturn(expectedProduct);

        Product actualProduct = productRepository.getProductBy(PRODUCT_TYPE, PRODUCT_ID);

        verify(mockedQuery).setParameter("productType", PRODUCT_TYPE);
        verify(mockedQuery).setParameter("productId", PRODUCT_ID);
        verify(mockedQuery).getSingleResult();
        assertEquals(actualProduct, expectedProduct);
    }

    @Test
    public void shouldGetAllProducts() {
        when(entityManager.createQuery(eq(EXPECTED_ALL_PRODUCT_QUERY), eq(Product.class))).thenReturn(mockedQuery);
        List<Product> expectedProductList = asList(buildProductDomain("Ferari", "10000$", "Car"), buildProductDomain("Activa", "500$", "Mopet"));
        when(mockedQuery.getResultList()).thenReturn(expectedProductList);

        Collection<Product> actualProductsList = productRepository.getAllProducts();

        verify(mockedQuery).getResultList();
        assertEquals(actualProductsList, expectedProductList);
    }

    private Product buildProductDomain(String name, String price, String car) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        ProductType productType = new ProductType();
        productType.setType(car);
        product.setProductType(productType);
        return product;
    }
}