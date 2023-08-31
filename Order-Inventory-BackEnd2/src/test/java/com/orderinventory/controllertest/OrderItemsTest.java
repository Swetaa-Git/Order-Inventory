package com.orderinventory.controllertest;

import com.orderinventory.controllers.OrderItemController;
import com.orderinventory.entities.OrderItem;
import com.orderinventory.exceptions.NotFoundException;
import com.orderinventory.services.OrderItemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class OrderItemsTest {

    @Mock
    private OrderItemServiceImpl orderItemServices;

    @InjectMocks
    private OrderItemController orderItemController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrderItem() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem());

        when(orderItemServices.getAllOrderItem()).thenReturn(orderItems);

        ResponseEntity<List<OrderItem>> response = orderItemController.getAllOrderItem();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderItems, response.getBody());

        verify(orderItemServices, times(1)).getAllOrderItem();
    }

    @Test(expected = NotFoundException.class)
    public void testGetAllOrderItem_notFound() {
        when(orderItemServices.getAllOrderItem()).thenReturn(null);

        orderItemController.getAllOrderItem();
    }
}
