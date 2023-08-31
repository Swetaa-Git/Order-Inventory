package com.orderinventory.controllers;

import java.math.BigDecimal;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.orderinventory.entities.Product;
import com.orderinventory.exceptions.InvaliddataException;
import com.orderinventory.exceptions.NotFoundException;
import com.orderinventory.services.ProductServiceImpl;
import com.orderinventory.utility.GlobalLogger;

/**
 * A class represents ProductController.
 * @RestController defines RESTful controller in SpringBoot.
 * It combines @controller & @ResponseBody.These two annotations indicates that the returned 
 * value from the methods should be serialized into HTTP response body.
 * @RequestMapping used to map HTTP requests to specific handler method in controller
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	

	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	
    @Autowired
	private ProductServiceImpl prod;

    /**
	 * Handles the GET request to get AllProducts.
	 * @return
	 */
    
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		
		String methodName = "getAllProducts()";
        logger.info(methodName + "called");
		
		List<Product> products = prod.getAllProducts();
		if (products.isEmpty()) {
			throw new NotFoundException("No products found");
		}
		return ResponseEntity.ok(products);
	}

	/**
	 * Handles the POST request to create new product.
	 * @param product
	 * @return
	 */
	
	@PostMapping
     public ResponseEntity<String> addProduct(@RequestBody Product product) {
		
		String methodName = "addProduct()";
        logger.info(methodName + "called");
		
		Product check = prod.addProduct(product);
		if (check != null) {
			return new ResponseEntity<String>(" Record Created Successfully",HttpStatus.CREATED) ;
		}
		throw new InvaliddataException("Failed to add product");
	}

	/**
	 * Handles the PUT request to updateProduct.
	 * @param product
	 * @return
	 */
	
	@PutMapping
	public ResponseEntity<String> updateProduct(@RequestBody Product product) {
		
		String methodName = "updateProduct()";
        logger.info(methodName + "called");
		
		Product check = prod.updateProduct(product);
		if (check != null) {
			return new ResponseEntity<String>( "Record Updated Successfully",HttpStatus.OK);
		}
		
		throw new InvaliddataException( " Invalid data founded to update ");
	}

	/**
	 * Handles the DELETE request to delete Product.
	 * @param id
	 * @return
	 */
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
		
		String methodName = "deleteProduct()";
        logger.info(methodName + "called");
		
	    String result = prod.deleteProductById(id);

	    if (!result.equals("deleted")) {
	        throw new NotFoundException("Product with ID " + id + " not found");
	    }

	    return ResponseEntity.ok("Product deleted successfully");
	}

	/**
	 * Handles the GET request to getProductsByName.
	 * @param productName
	 * @return
	 */

	@GetMapping("/{productname}")
	public ResponseEntity<List<Product>> getProductsByName(@PathVariable("productname") String productName) {
		
		String methodName = "getProductsByName()";
        logger.info(methodName + "called");
		
		List<Product> products = prod.getProductsByName(productName);

		if (products.isEmpty()) {
			throw new InvaliddataException(" productName not found with the name: " + productName);
		}

		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	/**
	 * Handles the GET request to getProductsByUnitPriceRange.
	 * @param min
	 * @param max
	 * @return
	 */
	
	@GetMapping("/unitprice")
	public ResponseEntity<List<Product>> getProductsByUnitPriceRange(@RequestParam("min") BigDecimal min,
			@RequestParam("max") BigDecimal max) {
		
		String methodName = "getProductsByUnitPriceRange()";
        logger.info(methodName + "called");
		
		List<Product> products = prod.getProductsByUnitPriceRange(min, max);

		if (products.isEmpty()) {
			throw new NotFoundException(" product Price Not found within the specified unit price range");
		}

		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	/**
	 * Handles the GET request to get SortedProductsByField.
	 * @param field
	 * @return
	 */
	
	@GetMapping("/sort")
	public ResponseEntity<List<Product>> getSortedProductsByField(@RequestParam("field") String field) {
		
		String methodName = "getSortedProductsByField()";
        logger.info(methodName + "called");
		
		Sort sort = Sort.by(field);
		List<Product> sortedProducts = prod.getSortedProductsByField(sort);

		if (sortedProducts.isEmpty()) {
			throw new NotFoundException("No products found for sorting field: " + field);
		}
		return ResponseEntity.ok(sortedProducts);
	}

}
