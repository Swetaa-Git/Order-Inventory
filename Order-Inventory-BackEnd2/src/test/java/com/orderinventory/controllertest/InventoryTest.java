package com.orderinventory.controllertest;

import com.orderinventory.controllers.InventoryController;
import com.orderinventory.dto.InventoryProductCustomerStore;
import com.orderinventory.dto.InventoryProductStoreShipmentStatusSum;
import com.orderinventory.dto.InventoryShipment;
import com.orderinventory.dto.InventoryStoreProductOrderStatus;
import com.orderinventory.dto.ShipmentStatusSoldProducts;
import com.orderinventory.entities.Inventory;
import com.orderinventory.exceptions.InvaliddataException;
import com.orderinventory.exceptions.NotFoundException;
import com.orderinventory.services.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryControllerTest {

    @Mock
    private InventoryServiceImpl inventoryServices;

    @InjectMocks
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInventory_WithInventoryItems_ReturnsInventoryList() {
        // Arrange
        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(new Inventory());
        when(inventoryServices.getAllInventory()).thenReturn(inventoryList);

        // Act
        ResponseEntity<List<Inventory>> response = inventoryController.getAllInventory();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inventoryList, response.getBody());
        verify(inventoryServices, times(1)).getAllInventory();
    }

    @Test
    void testGetAllInventory_NoInventoryItems_ThrowsNotFoundException() {
        // Arrange
        when(inventoryServices.getAllInventory()).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> inventoryController.getAllInventory());
        verify(inventoryServices, times(1)).getAllInventory();
    }

    @Test
    void testGetProductCustomerStoreByOrderId_WithValidOrderId_ReturnsInventoryProductCustomerStore() {
        // Arrange
        int orderId = 1;
        InventoryProductCustomerStore expectedResult = new InventoryProductCustomerStore();
        when(inventoryServices.getProductCustomerStoreByOrderId(orderId)).thenReturn(expectedResult);

        // Act
        ResponseEntity<InventoryProductCustomerStore> response = inventoryController.getProductCustomerStoreByOrderId(orderId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResult, response.getBody());
        verify(inventoryServices, times(1)).getProductCustomerStoreByOrderId(orderId);
    }

    @Test
    void testGetProductCustomerStoreByOrderId_WithInvalidOrderId_ThrowsInvaliddataException() {
        // Arrange
        int orderId = 1;
        when(inventoryServices.getProductCustomerStoreByOrderId(orderId)).thenReturn(null);

        // Act & Assert
        assertThrows(InvaliddataException.class, () -> inventoryController.getProductCustomerStoreByOrderId(orderId));
        verify(inventoryServices, times(1)).getProductCustomerStoreByOrderId(orderId);
    }

 // ...

    @Test
    void testGetInventoryDetailsByOrderId_WithValidOrderId_ReturnsInventoryDetails() {
        // Arrange
        int orderId = 1;
        List<InventoryProductStoreShipmentStatusSum> expectedResult = new ArrayList<>();
        expectedResult.add(new InventoryProductStoreShipmentStatusSum());
        when(inventoryServices.getInventoryDetailsByOrderId(orderId)).thenReturn(expectedResult);

        // Act
        ResponseEntity<List<InventoryProductStoreShipmentStatusSum>> response =
                inventoryController.getInventoryDetailsByOrderId(orderId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResult, response.getBody());
        verify(inventoryServices, times(1)).getInventoryDetailsByOrderId(orderId);
    }

    @Test
    void testGetInventoryDetailsByOrderId_WithInvalidOrderId_ThrowsNotFoundException() {
        // Arrange
        int orderId = 1;
        when(inventoryServices.getInventoryDetailsByOrderId(orderId)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> inventoryController.getInventoryDetailsByOrderId(orderId));
        verify(inventoryServices, times(1)).getInventoryDetailsByOrderId(orderId);
    }

    @Test
    void testGetProductOrderStatusByStoreId_WithValidStoreId_ReturnsInventoryStoreProductOrderStatus() {
        // Arrange
        int storeId = 1;
        InventoryStoreProductOrderStatus expectedResult = new InventoryStoreProductOrderStatus();
        when(inventoryServices.getProductOrderStatusByStoreId(storeId)).thenReturn(expectedResult);

        // Act
        ResponseEntity<InventoryStoreProductOrderStatus> response =
                inventoryController.getProductOrderStatusByStoreId(storeId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResult, response.getBody());
        verify(inventoryServices, times(1)).getProductOrderStatusByStoreId(storeId);
    }

    @Test
    void testGetProductOrderStatusByStoreId_WithInvalidStoreId_ThrowsNotFoundException() {
        // Arrange
        int storeId = 1;
        when(inventoryServices.getProductOrderStatusByStoreId(storeId)).thenReturn(null);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> inventoryController.getProductOrderStatusByStoreId(storeId));
        verify(inventoryServices, times(1)).getProductOrderStatusByStoreId(storeId);
    }

    @Test
    void testGetInventoryShipmentById_WithInventoryShipments_ReturnsInventoryShipmentList() {
        // Arrange
        List<InventoryShipment> inventoryShipments = new ArrayList<>();
        inventoryShipments.add(new InventoryShipment());
        when(inventoryServices.getInventoryShipmentById()).thenReturn(inventoryShipments);

        // Act
        ResponseEntity<List<InventoryShipment>> response = inventoryController.getInventoryShipmentById();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inventoryShipments, response.getBody());
        verify(inventoryServices, times(1)).getInventoryShipmentById();
    }

    @Test
    void testGetInventoryShipmentById_NoInventoryShipments_ThrowsNotFoundException() {
        // Arrange
        when(inventoryServices.getInventoryShipmentById()).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> inventoryController.getInventoryShipmentById());
        verify(inventoryServices, times(1)).getInventoryShipmentById();
    }

    @Test
    void testGetCountOfSoldProductsByShipmentStatus_ReturnsShipmentStatusSoldProducts() {
        // Arrange
        ShipmentStatusSoldProducts expectedResult = new ShipmentStatusSoldProducts();
        when(inventoryServices.getCountOfSoldProductsByShipmentStatus()).thenReturn(expectedResult);

        // Act
        ShipmentStatusSoldProducts result = inventoryController.getCountOfSoldProductsByShipmentStatus();

        // Assert
        assertEquals(expectedResult, result);
        verify(inventoryServices, times(1)).getCountOfSoldProductsByShipmentStatus();
    }


}