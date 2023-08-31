package com.orderinventory.servicetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.orderinventory.dao.OrderItemRepository;
import com.orderinventory.entities.OrderItem;
import com.orderinventory.services.OrderItemServiceImpl;

public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository repository;

    @InjectMocks
    private OrderItemServiceImpl service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllOrderItem() {
        // Arrange
        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();
        List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);
        when(repository.findAll()).thenReturn(orderItems);

        // Act
        List<OrderItem> result = service.getAllOrderItem();

        // Assert
        assertEquals(orderItems, result);
        verify(repository).findAll();
    }
}
