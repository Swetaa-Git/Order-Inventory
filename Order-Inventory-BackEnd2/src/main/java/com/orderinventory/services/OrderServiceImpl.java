package com.orderinventory.services;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderinventory.controllers.CustomerController;
import com.orderinventory.dao.OrderRepository;
import com.orderinventory.dto.OrderStatusCount;
import com.orderinventory.dto.OrderStoreList;
import com.orderinventory.entities.Order;
import com.orderinventory.utility.GlobalLogger;

/**
 * @Service  is used to mark a class as a service component.
 */
@Service("orderservice")
public class OrderServiceImpl implements OrderService{
	

	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * @Override informs the compiler that the element is meant to override an element declared in a superclass
	 */
	
	@Override
	public List<Order> getAllOrders(){
		String methodName = "getAllOrders()";
        logger.info(methodName + "called");
		return orderRepository.findAll();
	}
	
	
	@Override
	public Order addOrder(Order order) {
		String methodName = "addOrder()";
        logger.info(methodName + "called");
		return orderRepository.save(order);
	}
	
	
	@Override
	public Order updateOrder(Order order) {
		String methodName = "updateOrder()";
        logger.info(methodName + "called");
		Order updatedOrder = orderRepository.findById(order.getOrderId()).orElse(null);
		updatedOrder.setOrderStatus(order.getOrderStatus());
		updatedOrder.setCustomer(order.getCustomer());
		updatedOrder.setOrderTimestamp(order.getOrderTimestamp());
		updatedOrder.setStore(order.getStore());		
		
		return orderRepository.save(updatedOrder);
	}
	
	
	@Override
	public String deleteOrderById(int id) {
		String methodName = "deleteOrderById()";
        logger.info(methodName + "called");
		Order order = orderRepository.findById(id).orElse(null);
		if (order!=null) {
			orderRepository.deleteById(id);
			return "Record deleted Successfully";
		}
		return "failed";
	}
	
	
	@Override
	public List<OrderStatusCount> getOrderCountByStatus(String orderStatus){
		String methodName = "getOrderCountByStatus()";
        logger.info(methodName + "called");
		return orderRepository.getOrderCountByStatus(orderStatus);
	}
	
	
	@Override
	public List<OrderStoreList> getOrdersByStoreId(int id){
		String methodName = "getOrdersByStoreId()";
        logger.info(methodName + "called");
		return orderRepository.findByStoreId(id);
	}
}
