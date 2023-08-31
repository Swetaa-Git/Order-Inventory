package com.orderinventory.servicetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.orderinventory.dao.InventoryRepository;
import com.orderinventory.dao.ShipmentRepository;
import com.orderinventory.dao.StoreRepository;
import com.orderinventory.dto.InventoryProductCustomerStore;
import com.orderinventory.dto.InventoryProductStoreShipmentStatusSum;
import com.orderinventory.dto.InventoryShipment;
import com.orderinventory.dto.InventoryStoreProductOrderStatus;
import com.orderinventory.dto.ShipmentStatusSoldProducts;
import com.orderinventory.entities.Inventory;
import com.orderinventory.entities.Store;
import com.orderinventory.services.InventoryServiceImpl;

public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllInventory() {
        // Arrange
        Inventory inventory1 = new Inventory();
        Inventory inventory2 = new Inventory();
        List<Inventory> inventoryList = Arrays.asList(inventory1, inventory2);
        when(inventoryRepository.findAll()).thenReturn(inventoryList);

        // Act
        List<Inventory> result = inventoryService.getAllInventory();

        // Assert
        assertEquals(inventoryList, result);
        verify(inventoryRepository).findAll();
    }

    @Test
    public void testGetProductCustomerStoreByOrderId() {
        // Arrange
        int orderId = 1;
        InventoryProductCustomerStore productCustomerStore = new InventoryProductCustomerStore();
        when(inventoryRepository.findProductCustomerStoreByOrderId(orderId)).thenReturn(productCustomerStore);

        // Act
        InventoryProductCustomerStore result = inventoryService.getProductCustomerStoreByOrderId(orderId);

        // Assert
        assertEquals(productCustomerStore, result);
        verify(inventoryRepository).findProductCustomerStoreByOrderId(orderId);
    }

    @Test
    public void testGetInventoryDetailsByOrderId() {
        // Arrange
        int orderId = 1;
        InventoryProductStoreShipmentStatusSum details1 = new InventoryProductStoreShipmentStatusSum();
        InventoryProductStoreShipmentStatusSum details2 = new InventoryProductStoreShipmentStatusSum();
        List<InventoryProductStoreShipmentStatusSum> detailsList = Arrays.asList(details1, details2);
        when(inventoryRepository.findInventoryDetailsByOrderId(orderId)).thenReturn(detailsList);

        // Act
        List<InventoryProductStoreShipmentStatusSum> result = inventoryService.getInventoryDetailsByOrderId(orderId);

        // Assert
        assertEquals(detailsList, result);
        verify(inventoryRepository).findInventoryDetailsByOrderId(orderId);
    }

    @Test
    public void testGetProductOrderStatusByStoreId() {
        // Arrange
        int storeId = 1;
        Store store = new Store();
        InventoryStoreProductOrderStatus orderStatus = new InventoryStoreProductOrderStatus(store, new ArrayList<>());
        when(storeRepository.findById(storeId)).thenReturn(java.util.Optional.of(store));
        when(inventoryRepository.findProductOrderStatusByStoreId(storeId)).thenReturn(new ArrayList<>());

        // Act
        InventoryStoreProductOrderStatus result = inventoryService.getProductOrderStatusByStoreId(storeId);

        // Assert
        assertEquals(orderStatus, result);
        verify(storeRepository).findById(storeId);
        verify(inventoryRepository).findProductOrderStatusByStoreId(storeId);
    }

    @Test
    public void testGetInventoryShipmentById() {
        // Arrange
        List<Integer> allIds = Arrays.asList(1, 2, 3);
        InventoryShipment shipment1 = new InventoryShipment();
        InventoryShipment shipment2 = new InventoryShipment();
        InventoryShipment shipment3 = new InventoryShipment();
        List<InventoryShipment> expectedShipmentList = Arrays.asList(shipment1, shipment2, shipment3);

        when(shipmentRepository.findAllShipmentId()).thenReturn(allIds);
        when(inventoryRepository.findInventoryShipmentById(1)).thenReturn(shipment1);
        when(inventoryRepository.findInventoryShipmentById(2)).thenReturn(shipment2);
        when(inventoryRepository.findInventoryShipmentById(3)).thenReturn(shipment3);

        // Act
        List<InventoryShipment> result = inventoryService.getInventoryShipmentById();

        // Assert
        assertEquals(expectedShipmentList, result);
        verify(shipmentRepository).findAllShipmentId();
        verify(inventoryRepository).findInventoryShipmentById(1);
        verify(inventoryRepository).findInventoryShipmentById(2);
        verify(inventoryRepository).findInventoryShipmentById(3);
    }

    @Test
    public void testGetCountOfSoldProductsByShipmentStatus() {
        // Arrange
        ShipmentStatusSoldProducts soldProducts = new ShipmentStatusSoldProducts();
        when(inventoryRepository.findSoldProductsByShipmentStatus()).thenReturn(soldProducts);

        // Act
        ShipmentStatusSoldProducts result = inventoryService.getCountOfSoldProductsByShipmentStatus();

        // Assert
        assertEquals(soldProducts, result);
        verify(inventoryRepository).findSoldProductsByShipmentStatus();
    }
}
