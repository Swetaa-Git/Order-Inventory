package com.orderinventory.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.entities.OrderItem;
import com.orderinventory.exceptions.NotFoundException;
import com.orderinventory.services.OrderItemServiceImpl;
import com.orderinventory.utility.GlobalLogger;

/**
 * A class represents OrderItemController.
 * @RestController defines RESTful controller in SpringBoot.
 * It combines @controller & @ResponseBody.These two annotations indicates that the returned 
 * value from the methods should be serialized into HTTP response body.
 * @RequestMapping used to map HTTP requests to specific handler method in controller.
 */
@RestController
@RequestMapping("/api/v1/orderitems")
public class OrderItemController {
	

	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	
	@Autowired
	private OrderItemServiceImpl orderItem;
	
	/**
	 * Handles the GET request to get AllOrderItem.
	 * @return
	 */
	
	@GetMapping
	public ResponseEntity<List<OrderItem>> getAllOrderItem(){
		
		String methodName = "getAllOrderItem()";
        logger.info(methodName + "called");
		
		if(orderItem==null) {
			throw new NotFoundException("order item not found");
		}
		return new ResponseEntity<List<OrderItem>> (orderItem.getAllOrderItem(),HttpStatus.OK);
	}
}
