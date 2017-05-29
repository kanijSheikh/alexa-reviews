package com.sapient.repositories;

import com.sapient.exception.EntityNotFoundException;
import com.sapient.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public void add(Collection<Product> products) {
        for (Product product : products) {
            entityManager.persist(product);
        }
    }


    @Override
    public Collection<Product> getProductsByType(String productType) {
        String query = "FROM Product AS product where product.productType.type = :productType";
        TypedQuery<Product> q = entityManager.createQuery(query, Product.class);
        q.setParameter("productType", productType);
        return q.getResultList();
    }

    @Override
    public Product getProductById(Integer productId) {
        String query = "FROM Product p where p.id =:productId";
        TypedQuery<Product> q = entityManager.createQuery(query, Product.class);
        q.setParameter("productId", productId);
        return q.getSingleResult();
    }

    @Override
    public Product getProductBy(String productType, Integer productId) {
        String query = "FROM Product p where p.productType.type =:productType and p.id =:productId";
        TypedQuery<Product> q = entityManager.createQuery(query, Product.class);
        q.setParameter("productType", productType);
        q.setParameter("productId", productId);
        return q.getSingleResult();
    }

    @Override
    public Collection<Product> getAllProducts() {
        String query = "FROM Product AS p";
        return entityManager.createQuery(query, Product.class).getResultList();
    }

    @Transactional
    @Override
    public void remove(Integer productId) {
        Product product = entityManager.find(Product.class, productId);
        if (product == null) {
            throw new EntityNotFoundException();
        }
        entityManager.remove(product);
    }


}
