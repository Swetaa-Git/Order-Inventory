package com.orderinventory.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.orderinventory.dto.OrderStatusCount;
import com.orderinventory.dto.OrderStoreList;
import com.orderinventory.entities.Order;
import com.orderinventory.exceptions.InvaliddataException;
import com.orderinventory.exceptions.NotFoundException;
import com.orderinventory.services.OrderServiceImpl;
import com.orderinventory.utility.GlobalLogger;

/**
 * A class represents OrderController.
 * @RestController defines RESTful controller in SpringBoot.It combines @controller & @ResponseBody.
 * These two annotations indicates that the returned 
 * value from the methods should be serialized into HTTP response body.
 * @RequestMapping used to map HTTP requests to specific handler method in controller.
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
	

	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	

	@Autowired
	private OrderServiceImpl orderServices;

	/**
	 * Handles the GET request to get AllOrders.
	 * @return
	 */
	
	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		
		String methodName = "getAllOrders()";
        logger.info(methodName + "called");
		
		
		List<Order> orders = orderServices.getAllOrders();

		if (orders.isEmpty()) {
			throw new NotFoundException("No orders found");
		}

		return ResponseEntity.ok(orders);
	}
	
	/**
	 * Handles the POST request to create new Order.
	 * @param order
	 * @return
	 */
	
	@PostMapping
	public ResponseEntity<String> addOrder(@RequestBody Order order) {
		
		String methodName = "addOrder()";
        logger.info(methodName + "called");
		
		Order addedOrder = orderServices.addOrder(order);
		
		if(addedOrder != null) {
			return ResponseEntity.ok("Record created Successfully");
		}
		throw new InvaliddataException("Record creation has failed!");
	}

	/**
	 * Handles the PUT request to updateOrder.
	 * @param order
	 * @return
	 */
	@PutMapping
	public ResponseEntity<String> updateOrder(@RequestBody Order order) {
		
		String methodName = "updateOrder()";
        logger.info(methodName + "called");
		
		Order updatedOrder = orderServices.updateOrder(order);

		if (updatedOrder != null) {
			return ResponseEntity.ok("Record Updated Successfully");
		} else {
			throw new NotFoundException("No order found for the provided ID: " + order.getOrderId());
		}
	}

	/**
	 * Handles the DELETE request to delete order.
	 * @param id
	 * @return
	 */
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOrder(@PathVariable("id") int id) {
		
		String methodName = "deleteOrder()";
        logger.info(methodName + "called");
		
		String result = orderServices.deleteOrderById(id);

		if (result.equals("failed")) {
			throw new NotFoundException("No order found for the provided ID: " + id);
		}

		return new ResponseEntity<String>( result,HttpStatus.OK);
	}

	/**
	 * Handles the get request to get OrderCountByStatus.
	 * @param orderStatus
	 * @return
	 */
	@GetMapping("/status")
	public ResponseEntity<List<OrderStatusCount>> getOrderCountByStatus(
			@RequestParam("orderstatus") String orderStatus) {
		
		String methodName = "getOrderCountByStatus()";
        logger.info(methodName + "called");
		
		List<OrderStatusCount> orderCountList = orderServices.getOrderCountByStatus(orderStatus);

		if (orderCountList.isEmpty()) {
			throw new InvaliddataException("No orders found for the provided order status: " + orderStatus);
		}

		return ResponseEntity.ok(orderCountList);
	}

	/**
	 * Handles the GET request to get OrderListByStoreId.
	 * @param storeId
	 * @return
	 */
	
	@GetMapping("/store")
	public ResponseEntity<List<OrderStoreList>> getOrderListByStoreId(@RequestParam("storeid") int storeId) {
		
		String methodName = "getOrderListByStoreId()";
        logger.info(methodName + "called");
		
		List<OrderStoreList> orderList = orderServices.getOrdersByStoreId(storeId);

		if (orderList.isEmpty()) {
			throw new NotFoundException("No orders found for the provided store ID: " + storeId);
		}

		return ResponseEntity.ok(orderList);
	}
}
