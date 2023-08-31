package com.orderinventory.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.entities.Customer;
import com.orderinventory.entities.Store;
import com.orderinventory.exceptions.NotFoundException;
import com.orderinventory.services.StoreServiceImpl;
import com.orderinventory.utility.GlobalLogger;

/**
 * A class represents StoreController
 * @RestController defines RESTful controller in SpringBoot.
 * It combines @controller & @ResponseBody.These two annotations indicates that the returned 
 * value from the methods should be serialized into HTTP response body.
 * @RequestMapping used to map HTTP requests to specific handler method in controller.
 */

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {


	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	
	@Autowired
	private StoreServiceImpl store;

	/**
	 * Handles the GET request to get AllStores.
	 * @return
	 */
	
	@GetMapping
	public ResponseEntity<List<Store>> getAllStores() {
		
		String methodName = "getAllStores()";
        logger.info(methodName + "called");
		
		List<Store> stores = store.getAllStores();

		if (stores.isEmpty()) {
			throw new NotFoundException("No stores found");
		}

		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}

}
