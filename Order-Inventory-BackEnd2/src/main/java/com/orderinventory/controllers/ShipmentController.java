package com.orderinventory.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.entities.Shipment;
import com.orderinventory.exceptions.InvaliddataException;
import com.orderinventory.services.ShipmentServiceImpl;
import com.orderinventory.utility.GlobalLogger;

/**
 * A class represents ShipmentController.
 * @RestController defines RESTful controller in SpringBoot.
 * It combines @controller & @ResponseBody.
 * These two annotations indicates that the returned 
 * value from the methods should be serialized into HTTP response body.
 * @RequestMapping used to map HTTP requests to specific handler method in controller.
 */

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {
	

	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	
	@Autowired
	private ShipmentServiceImpl shipment;
	
	/**
	 * Handles the GET request to get AllShipments.
	 * @return
	 */
	
	@GetMapping
	public ResponseEntity<List<Shipment>> getAllShipments(){
		
		String methodName = "getAllShipments()";
        logger.info(methodName + "called");
		
		List<Shipment> s=shipment.getAllShipment();
		if(shipment==null) {
			throw new InvaliddataException("No shipment is found with this name:"+shipment);
		}
		return new ResponseEntity<List<Shipment>>(s,HttpStatus.OK);
	}
}
