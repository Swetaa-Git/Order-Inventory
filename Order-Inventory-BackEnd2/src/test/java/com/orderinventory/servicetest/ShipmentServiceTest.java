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

import com.orderinventory.dao.ShipmentRepository;
import com.orderinventory.entities.Shipment;
import com.orderinventory.services.ShipmentServiceImpl;

public class ShipmentServiceTest {

    @Mock
    private ShipmentRepository repository;

    @InjectMocks
    private ShipmentServiceImpl service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllShipment() {
        // Arrange
        Shipment shipment1 = new Shipment();
        Shipment shipment2 = new Shipment();
        List<Shipment> shipments = Arrays.asList(shipment1, shipment2);
        when(repository.findAll()).thenReturn(shipments);

        // Act
        List<Shipment> result = service.getAllShipment();

        // Assert
        assertEquals(shipments, result);
        verify(repository).findAll();
    }
}

