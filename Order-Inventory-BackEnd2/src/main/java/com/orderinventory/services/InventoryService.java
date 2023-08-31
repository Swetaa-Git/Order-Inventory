package com.orderinventory.services;

import java.util.List;

import com.orderinventory.dto.InventoryProductCustomerStore;
import com.orderinventory.dto.InventoryProductStoreShipmentStatusSum;
import com.orderinventory.dto.InventoryShipment;
import com.orderinventory.dto.InventoryStoreProductOrderStatus;
import com.orderinventory.dto.ShipmentStatusSoldProducts;
import com.orderinventory.entities.Inventory;

public interface InventoryService {

	List<Inventory> getAllInventory();

	InventoryProductCustomerStore getProductCustomerStoreByOrderId(int orderId);

	List<InventoryProductStoreShipmentStatusSum> getInventoryDetailsByOrderId(int orderId);

	InventoryStoreProductOrderStatus getProductOrderStatusByStoreId(int storeId);

	List<InventoryShipment> getInventoryShipmentById();

	ShipmentStatusSoldProducts getCountOfSoldProductsByShipmentStatus();

}
