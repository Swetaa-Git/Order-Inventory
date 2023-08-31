package com.orderinventory.controllertest;

import com.orderinventory.controllers.ShipmentController;
import com.orderinventory.entities.Shipment;
import com.orderinventory.exceptions.InvaliddataException;
import com.orderinventory.services.ShipmentServiceImpl;
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
public class ShipmentsTest {

    @Mock
    private ShipmentServiceImpl shipmentServices;

    @InjectMocks
    private ShipmentController shipmentController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllShipments() {
        List<Shipment> shipments = new ArrayList<>();
        shipments.add(new Shipment());

        when(shipmentServices.getAllShipment()).thenReturn(shipments);

        ResponseEntity<List<Shipment>> response = shipmentController.getAllShipments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shipments, response.getBody());

        verify(shipmentServices, times(1)).getAllShipment();
    }

    @Test(expected = InvaliddataException.class)
    public void testGetAllShipments_notFound() {
        when(shipmentServices.getAllShipment()).thenReturn(null);

        shipmentController.getAllShipments();
    }
}
