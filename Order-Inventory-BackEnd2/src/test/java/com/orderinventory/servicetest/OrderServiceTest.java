package com.orderinventory.servicetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.orderinventory.dao.OrderRepository;
import com.orderinventory.dto.OrderStatusCount;
import com.orderinventory.dto.OrderStoreList;
import com.orderinventory.entities.Order;
import com.orderinventory.services.OrderServiceImpl;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = Arrays.asList(order1, order2);
        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<Order> result = orderService.getAllOrders();

        // Assert
        assertEquals(orders, result);
        verify(orderRepository).findAll();
    }

    @Test
    public void testAddOrder() {
        // Arrange
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order result = orderService.addOrder(order);

        // Assert
        assertEquals(order, result);
        verify(orderRepository).save(order);
    }

    @Test
    public void testUpdateOrder() {
        // Arrange
        int orderId = 1;
        Order existingOrder = new Order();
        existingOrder.setOrderId(orderId);
        Order updatedOrder = new Order();
        updatedOrder.setOrderId(orderId);
        updatedOrder.setOrderStatus("Updated Status");

        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(existingOrder));
        when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        // Act
        Order result = orderService.updateOrder(updatedOrder);

        // Assert
        assertEquals(updatedOrder, result);
        assertEquals("Updated Status", existingOrder.getOrderStatus());
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(updatedOrder);
    }

    @Test
    public void testDeleteOrderByIdExists() {
        // Arrange
        int orderId = 1;
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(order));

        // Act
        String result = orderService.deleteOrderById(orderId);

        // Assert
        assertEquals("Record deleted Successfully", result);
        verify(orderRepository).findById(orderId);
        verify(orderRepository).deleteById(orderId);
    }

    @Test
    public void testDeleteOrderByIdNotExists() {
        // Arrange
        int orderId = 1;
        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.empty());

        // Act
        String result = orderService.deleteOrderById(orderId);

        // Assert
        assertEquals("failed", result);
        verify(orderRepository).findById(orderId);
        verify(orderRepository, never()).deleteById(orderId);
    }

    @Test
    public void testGetOrderCountByStatus() {
        // Arrange
        String orderStatus = "Shipped";
        OrderStatusCount count = new OrderStatusCount();
        when(orderRepository.getOrderCountByStatus(orderStatus)).thenReturn(Arrays.asList(count));

        // Act
        List<OrderStatusCount> result = orderService.getOrderCountByStatus(orderStatus);

        // Assert
        assertEquals(Arrays.asList(count), result);
        verify(orderRepository).getOrderCountByStatus(orderStatus);
    }

    @Test
    public void testGetOrdersByStoreId() {
        // Arrange
        int storeId = 1;
        OrderStoreList order1 = new OrderStoreList();
        OrderStoreList order2 = new OrderStoreList();
        List<OrderStoreList> orders = Arrays.asList(order1, order2);

        when(orderRepository.findByStoreId(storeId)).thenReturn(orders);

        // Act
        List<OrderStoreList> result = orderService.getOrdersByStoreId(storeId);

        // Assert
        assertEquals(orders, result);
        verify(orderRepository).findByStoreId(storeId);
    }
}
