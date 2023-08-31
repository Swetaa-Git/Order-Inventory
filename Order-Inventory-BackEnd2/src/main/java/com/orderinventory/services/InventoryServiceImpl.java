package com.orderinventory.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.orderinventory.controllers.CustomerController;
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
import com.orderinventory.utility.GlobalLogger;

/**
 *  @Service  is used to mark a class as a service component.
 */

@Service
public class InventoryServiceImpl implements InventoryService{
	

	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private ShipmentRepository shipmentRepository;
	
	/**
	 * @Override informs the compiler that the element is meant to override an element declared in a superclass
	 */
	
	@Override
	public List<Inventory> getAllInventory(){
		String methodName = "getAllInventory()";
        logger.info(methodName + "called");
		return inventoryRepository.findAll();
	}
	
	
	@Override
	public InventoryProductCustomerStore getProductCustomerStoreByOrderId(int orderId) {
		String methodName = "getProductCustomerStoreByOrderId()";
        logger.info(methodName + "called");
		return inventoryRepository.findProductCustomerStoreByOrderId(orderId);
	}
	
	
	@Override
	public List<InventoryProductStoreShipmentStatusSum> getInventoryDetailsByOrderId(@Param("orderId") int orderId){
		String methodName = "getInventoryDetailsByOrderId()";
        logger.info(methodName + "called");
		return inventoryRepository.findInventoryDetailsByOrderId(orderId);
	}
	
	
	@Override
	public InventoryStoreProductOrderStatus getProductOrderStatusByStoreId(int storeId) {
		String methodName = "getProductOrderStatusByStoreId()";
        logger.info(methodName + "called");
		Store store = storeRepository.findById(storeId).orElse(null);
		List<Object[]> object = inventoryRepository.findProductOrderStatusByStoreId(storeId);
		return new InventoryStoreProductOrderStatus(store,object);
	}
	
	
	@Override
	public List<InventoryShipment> getInventoryShipmentById(){
		String methodName = "getInventoryShipmentById()";
        logger.info(methodName + "called");
		List<Integer> allId = shipmentRepository.findAllShipmentId();
		List<InventoryShipment> inventoryShipment = new ArrayList<>();
		for(int id : allId) {
			inventoryShipment.add(inventoryRepository.findInventoryShipmentById(id));
		}
		return inventoryShipment;
	}
	
	
	@Override
	public ShipmentStatusSoldProducts getCountOfSoldProductsByShipmentStatus() {
		String methodName = "getCountOfSoldProductsByShipmentStatus()";
        logger.info(methodName + "called");
		return inventoryRepository.findSoldProductsByShipmentStatus();
	}
}
