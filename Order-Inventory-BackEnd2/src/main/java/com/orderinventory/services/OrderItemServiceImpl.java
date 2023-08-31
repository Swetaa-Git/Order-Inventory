package com.orderinventory.services;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderinventory.controllers.CustomerController;
import com.orderinventory.dao.OrderItemRepository;
import com.orderinventory.entities.OrderItem;
import com.orderinventory.utility.GlobalLogger;

/**
 * @Service  is used to mark a class as a service component.
 */

@Service("orderitems")
public class OrderItemServiceImpl implements OrderItemService {
	

	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */

	@Autowired
	private OrderItemRepository repo;
	
	/**
	 * @Override informs the compiler that the element is meant to override an element declared in a superclass
	 */
	
	@Override
	public List<OrderItem> getAllOrderItem(){
		String methodName = "getAllOrderItem()";
        logger.info(methodName + "called");
		return repo.findAll();
	}
}
