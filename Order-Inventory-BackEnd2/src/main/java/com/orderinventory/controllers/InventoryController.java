package com.orderinventory.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.dao.InventoryRepository;
import com.orderinventory.dto.InventoryProductCustomerStore;
import com.orderinventory.dto.InventoryProductStoreShipmentStatusSum;
import com.orderinventory.dto.InventoryShipment;
import com.orderinventory.dto.InventoryStoreProductOrderStatus;
import com.orderinventory.dto.ShipmentStatusSoldProducts;
import com.orderinventory.entities.Inventory;
import com.orderinventory.exceptions.InvaliddataException;
import com.orderinventory.exceptions.NotFoundException;
import com.orderinventory.services.InventoryServiceImpl;
import com.orderinventory.utility.GlobalLogger;


/**
 * A class represents CustomerController.
 * @RestController defines RESTful controller in SpringBoot.
 * It combines @controller & @ResponseBody.These two annotations indicates that the returned 
 * value from the methods should be serialized into HTTP response body.
 * @RequestMapping used to map HTTP requests to specific handler method in controller.
 */

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
	
	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 *  @Autowired is used for automatic dependency injection in Spring Boot.
	 *  It allows spring to automatically wire beans & dependencies.
	 */

	@Autowired
	private InventoryServiceImpl inventoryServices;

	/**
	 * @RestController defines RESTful controller in SpringBoot.
	 * It combines @controller & @ResponseBody.These two annotations indicates that the returned 
	 * @RequestMapping used to map HTTP requests to specific handler method in controller.
	 * @return
	 */
	
	@GetMapping
	public ResponseEntity<List<Inventory>> getAllInventory() {
		
		String methodName = "getAllInventory()";
        logger.info(methodName + "called");
		
		List<Inventory> inventoryList = inventoryServices.getAllInventory();

		if (inventoryList.isEmpty()) {
			throw new NotFoundException("No inventory items found");
		}
		return ResponseEntity.ok(inventoryList);
	}

	/**
	 * Handles the GET request to get ProductCustomerStoreByOrderId.
	 * @param orderId
	 * @return
	 */
	
	@GetMapping("/orderid/{id}")
	public ResponseEntity<InventoryProductCustomerStore> getProductCustomerStoreByOrderId(
			@PathVariable("id") int orderId) {

		String methodName = "getProductCustomerStoreByOrderId()";
        logger.info(methodName + "called");
        
		InventoryProductCustomerStore result = inventoryServices.getProductCustomerStoreByOrderId(orderId);

		if (result == null) {
			throw new InvaliddataException(
					"No inventory product customer store found for the provided order ID: " + orderId);
		}

		return ResponseEntity.ok(result);
	}


	/**
	 * Handles the GET request to get InventoryDetailsByOrderId.
	 * @param orderId
	 * @return
	 */
	@GetMapping("/{orderid}/details")
	public ResponseEntity<List<InventoryProductStoreShipmentStatusSum>> getInventoryDetailsByOrderId(@PathVariable("orderid") int orderId) {
		String methodName = "getInventoryDetailsByOrderId()";
        logger.info(methodName + "called");
		
		List<InventoryProductStoreShipmentStatusSum> result = inventoryServices.getInventoryDetailsByOrderId(orderId);

		if (result.isEmpty()) {
			throw new NotFoundException("No inventory details found for the provided order ID: " + orderId);
		}

		return ResponseEntity.ok(result);
	}

	/**
	 * Handles the get request to get ProductOrderStatusByStoreId.
	 * @param storeId
	 * @return
	 */
	
	@GetMapping("/store")
	public ResponseEntity<InventoryStoreProductOrderStatus> getProductOrderStatusByStoreId(@RequestParam("storeid") int storeId) {
		String methodName = "getProductOrderStatusByStoreId()";
        logger.info(methodName + "called");
		
		InventoryStoreProductOrderStatus result = inventoryServices.getProductOrderStatusByStoreId(storeId);

		if (result == null) {
			throw new NotFoundException(
					"No product, order, or status details found for the provided store ID: " + storeId);
		}

		return new ResponseEntity<InventoryStoreProductOrderStatus>( result,HttpStatus.OK);
	}

	/**
	 * Handles the GET request to get InventoryShipmentById.
	 * @return
	 */
	@GetMapping("/shipmentid")
	public ResponseEntity<List<InventoryShipment>> getInventoryShipmentById() {
		String methodName = "getInventoryShipmentById()";
        logger.info(methodName + "called");
		
		List<InventoryShipment> inventoryShipments = inventoryServices.getInventoryShipmentById();

		if (inventoryShipments.isEmpty()) {
			throw new NotFoundException("No inventory shipment details found");
		}

		return new ResponseEntity<List<InventoryShipment>>(inventoryShipments,HttpStatus.OK);
	}

	/**
	 * Handles the GET request to get CountOfSoldProductByShipmentStatus
	 * @return
	 */
	@GetMapping("/shipment/soldproducts")
	public ShipmentStatusSoldProducts getCountOfSoldProductsByShipmentStatus() {
		String methodName = "getCountOfSoldProductsByShipmentStatus()";
        logger.info(methodName + "called");
		
		return inventoryServices.getCountOfSoldProductsByShipmentStatus();
	}
}