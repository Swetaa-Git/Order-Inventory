package com.orderinventory.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.orderinventory.entities.Product;

public interface ProductService {

	List<Product> getAllProducts();

	Product addProduct(Product product);

	Product updateProduct(Product product);

	String deleteProductById(int id);

	List<Product> getProductsByName(String productName);

	List<Product> getProductsByUnitPriceRange(BigDecimal min, BigDecimal max);

	List<Product> getSortedProductsByField(Sort sort);

}
