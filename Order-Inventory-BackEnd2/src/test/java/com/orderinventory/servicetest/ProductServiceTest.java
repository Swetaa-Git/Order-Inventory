package com.orderinventory.servicetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import com.orderinventory.dao.ProductRepository;
import com.orderinventory.entities.Product;
import com.orderinventory.services.ProductServiceImpl;

public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);
        when(repository.findAll()).thenReturn(products);

        // Act
        List<Product> result = service.getAllProducts();

        // Assert
        assertEquals(products, result);
        verify(repository).findAll();
    }

    @Test
    public void testAddProduct() {
        // Arrange
        Product product = new Product();
        when(repository.saveAndFlush(product)).thenReturn(product);

        // Act
        Product result = service.addProduct(product);

        // Assert
        assertEquals(product, result);
        verify(repository).saveAndFlush(product);
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        int productId = 1;
        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        Product updatedProduct = new Product();
        updatedProduct.setProductId(productId);
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setBrand("Updated Brand");
        updatedProduct.setColour("Updated Colour");
        updatedProduct.setUnitPrice(BigDecimal.valueOf(9.99));
        updatedProduct.setSize("Updated Size");
        updatedProduct.setRating((int) 4.5);

        when(repository.findById(productId)).thenReturn(java.util.Optional.of(existingProduct));
        when(repository.save(updatedProduct)).thenReturn(updatedProduct);

        // Act
        Product result = service.updateProduct(updatedProduct);

        // Assert
        assertEquals(updatedProduct, result);
        assertEquals("Updated Product", existingProduct.getProductName());
        assertEquals("Updated Brand", existingProduct.getBrand());
        assertEquals("Updated Colour", existingProduct.getColour());
        assertEquals(BigDecimal.valueOf(9.99), existingProduct.getUnitPrice());
        assertEquals("Updated Size", existingProduct.getSize());
        assertEquals(4.5, existingProduct.getRating(), 0.001);
        verify(repository).findById(productId);
        verify(repository).save(updatedProduct);
    }

    @Test
    public void testDeleteProductByIdExists() {
        // Arrange
        int productId = 1;
        Product product = new Product();
        when(repository.findById(productId)).thenReturn(java.util.Optional.of(product));

        // Act
        String result = service.deleteProductById(productId);

        // Assert
        assertEquals("deleted", result);
        verify(repository).findById(productId);
        verify(repository).deleteById(productId);
    }

    @Test
    public void testDeleteProductByIdNotExists() {
        // Arrange
        int productId = 1;
        when(repository.findById(productId)).thenReturn(java.util.Optional.empty());

        // Act
        String result = service.deleteProductById(productId);

        // Assert
        assertEquals("Record is not deleted", result);
        verify(repository).findById(productId);
        verify(repository, never()).deleteById(productId);
    }

    @Test
    public void testGetProductsByName() {
        // Arrange
        String productName = "Test Product";
        Product product = new Product();
        when(repository.findByProductName(productName)).thenReturn(Arrays.asList(product));

        // Act
        List<Product> result = service.getProductsByName(productName);

        // Assert
        assertEquals(Arrays.asList(product), result);
        verify(repository).findByProductName(productName);
    }

    @Test
    public void testGetProductsByUnitPriceRange() {
        // Arrange
        BigDecimal minPrice = BigDecimal.valueOf(10.0);
        BigDecimal maxPrice = BigDecimal.valueOf(100.0);
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);
        when(repository.findByUnitPriceBetween(minPrice, maxPrice)).thenReturn(products);

        // Act
        List<Product> result = service.getProductsByUnitPriceRange(minPrice, maxPrice);

        // Assert
        assertEquals(products, result);
        verify(repository).findByUnitPriceBetween(minPrice, maxPrice);
    }

    @Test
    public void testGetSortedProductsByField() {
        // Arrange
        Sort sort = Sort.by("productName").ascending();
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);
        when(repository.findAll(sort)).thenReturn(products);

        // Act
        List<Product> result = service.getSortedProductsByField(sort);

        // Assert
        assertEquals(products, result);
        verify(repository).findAll(sort);
    }
}
