package com.orderinventory.services;

import java.util.List;

import com.orderinventory.dto.OrderStatusCount;
import com.orderinventory.dto.OrderStoreList;
import com.orderinventory.entities.Order;

public interface OrderService {

	List<Order> getAllOrders();

	Order addOrder(Order order);

	Order updateOrder(Order order);

	String deleteOrderById(int id);

	List<OrderStatusCount> getOrderCountByStatus(String orderStatus);

	List<OrderStoreList> getOrdersByStoreId(int id);

}
