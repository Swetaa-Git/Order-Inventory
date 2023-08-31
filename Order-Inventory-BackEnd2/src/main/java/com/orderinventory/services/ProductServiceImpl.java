package com.orderinventory.services;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.orderinventory.controllers.CustomerController;
import com.orderinventory.dao.ProductRepository;
import com.orderinventory.entities.Product;
import com.orderinventory.utility.GlobalLogger;

import jakarta.transaction.Transactional;

/**
 * @Service  is used to mark a class as a service component.
 */
@Service
public class ProductServiceImpl implements ProductService{
	

	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	
	
	@Autowired
	private ProductRepository repo;
	
	/**
	 * @Override informs the compiler that the element is meant to override an element declared in a superclass
	 */
	
	@Override
	public List<Product> getAllProducts(){
		String methodName = "getAllProducts()";
        logger.info(methodName + "called");
		return repo.findAll();
	}
	
	
	@Override
	public Product addProduct(Product product) {
		String methodName = "addProduct()";
        logger.info(methodName + "called");
		return repo.saveAndFlush(product);
	}
	
	
	@Override
	public Product updateProduct(Product product) {
		String methodName = "updateProduct()";
        logger.info(methodName + "called");
		Product prod = repo.findById(product.getProductId()).orElse(null);
		prod.setProductName(product.getProductName());
		prod.setBrand(product.getBrand());
		prod.setColour(product.getColour());
		prod.setUnitPrice(product.getUnitPrice());
		prod.setSize(product.getSize());
		prod.setRating(product.getRating());
		return repo.save(prod);
	}
	
	
	@Override
	public String deleteProductById(int id) {
		String methodName = "deleteProductById()";
        logger.info(methodName + "called");
		Product product = repo.findById(id).orElse(null);
		if (product==null) {
			return "Record is not deleted";
		}
		repo.deleteById(id);
		return "deleted";
	}
	
	
	@Override
	public List<Product> getProductsByName(String productName){
		String methodName = "getProductsByName()";
        logger.info(methodName + "called");
		return repo.findByProductName(productName);
	}
	
	
	@Override
	public List<Product> getProductsByUnitPriceRange(BigDecimal min, BigDecimal max){
		String methodName = "getProductsByUnitPriceRange()";
        logger.info(methodName + "called");
		return repo.findByUnitPriceBetween(min, max);
		
	}
	
	@Override
	public List<Product> getSortedProductsByField(Sort sort){
		String methodName = "getSortedProductsByField()";
        logger.info(methodName + "called");
		return repo.findAll(sort);
	}
}
