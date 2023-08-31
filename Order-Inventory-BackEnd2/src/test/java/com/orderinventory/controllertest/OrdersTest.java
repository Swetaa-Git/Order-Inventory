package com.orderinventory.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.orderinventory.controllers.OrderController;
import com.orderinventory.dto.OrderStatusCount;
import com.orderinventory.dto.OrderStoreList;
import com.orderinventory.entities.Order;
import com.orderinventory.exceptions.InvaliddataException;
import com.orderinventory.exceptions.NotFoundException;
import com.orderinventory.services.OrderServiceImpl;

@RunWith(SpringRunner.class)
public class OrdersTest {

    @Mock
    private OrderServiceImpl orderServices;

    @InjectMocks
    private OrderController orderController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());

        when(orderServices.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());

        verify(orderServices, times(1)).getAllOrders();
    }



    @Test
    public void testAddOrder() {
        Order order = new Order();

        when(orderServices.addOrder(order)).thenReturn(order);

        ResponseEntity<String> response = orderController.addOrder(order);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record created Successfully", response.getBody());

        verify(orderServices, times(1)).addOrder(order);
    }

 
    @Test
    public void testUpdateOrder() {
        Order order = new Order();
        order.setOrderId(1);

        when(orderServices.updateOrder(order)).thenReturn(order);

        ResponseEntity<String> response = orderController.updateOrder(order);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record Updated Successfully", response.getBody());

        verify(orderServices, times(1)).updateOrder(order);
    }



    @Test
    public void testDeleteOrder() {
        int orderId = 1;
        String result = "success";

        when(orderServices.deleteOrderById(orderId)).thenReturn(result);

        ResponseEntity<String> response = orderController.deleteOrder(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(result, response.getBody());

        verify(orderServices, times(1)).deleteOrderById(orderId);
    }

 

    @Test
    public void testGetOrderCountByStatus() {
        String orderStatus = "completed";
        List<OrderStatusCount> orderCountList = new ArrayList<>();
        orderCountList.add(new OrderStatusCount());

        when(orderServices.getOrderCountByStatus(orderStatus)).thenReturn(orderCountList);

        ResponseEntity<List<OrderStatusCount>> response = orderController.getOrderCountByStatus(orderStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderCountList, response.getBody());

        verify(orderServices, times(1)).getOrderCountByStatus(orderStatus);
    }



    @Test
    public void testGetOrderListByStoreId() {
        int storeId = 1;
        List<OrderStoreList> orderList = new ArrayList<>();
        orderList.add(new OrderStoreList());

        when(orderServices.getOrdersByStoreId(storeId)).thenReturn(orderList);

        ResponseEntity<List<OrderStoreList>> response = orderController.getOrderListByStoreId(storeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderList, response.getBody());

        verify(orderServices, times(1)).getOrdersByStoreId(storeId);
    }


}
