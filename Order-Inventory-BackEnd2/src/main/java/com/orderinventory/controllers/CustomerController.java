package com.orderinventory.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.dto.CustomerOrders;
import com.orderinventory.dto.CustomerShipment;
import com.orderinventory.dto.ShipmentStatusCountCustomer;
import com.orderinventory.entities.Customer;
import com.orderinventory.exceptions.InvaliddataException;
import com.orderinventory.exceptions.NotFoundException;
import com.orderinventory.services.CustomerServiceImpl;
import com.orderinventory.utility.GlobalLogger;

/**
 * A class represents CustomerController.
 * @RestController defines RESTful controller in SpringBoot.
 * It combines @controller & @ResponseBody.These two annotations indicates that the returned 
 * value from the methods should be serialized into HTTP response body.
 * @RequestMapping used to map HTTP requests to specific handler method in controller.
 */

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	
	@Autowired
	private CustomerServiceImpl cust;

	/**
	 * Handles the GET request to get all Customers.
	 * ResponseEntity represents the entire HTTP response, including the status code, headers, and body.
	 * @RequestParam bind a request parameter to a method parameter in a controller method.
	 * @param fullName
	 * @return
	 */
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers(
			@RequestParam(name = "name", required = false) String fullName) {
		String methodName = "getAllCustomers()";
        logger.info(methodName + "called");
		if (fullName != null) {
			List<Customer> customersByFullName = cust.getCustomersByFullName(fullName);
			if (customersByFullName.isEmpty()) {
				throw new NotFoundException("Name is not found");
			}
			return ResponseEntity.ok(customersByFullName);
		} else {
			List<Customer> allCustomers = cust.getAllCustomers();
			if (allCustomers.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(allCustomers);
			}
			return ResponseEntity.ok(allCustomers);
		}
	}
	
	/**
	 * Handles a POST request to create a new account for the given customer.
	 * @param customer
	 * @return
	 */
	

	@PostMapping
	public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
		
		String methodName = "addCustomer()";
        logger.info(methodName + "called");
		
		Customer c1 = cust.addCustomer(customer);

		if (c1 == null) {
			throw new InvaliddataException("Record Created failed");
		}
		return new ResponseEntity<String>("Record Creation successfully", HttpStatus.CREATED);
	}
	
	/**
	 * Handles a PUT request to update customer
	 * @param customer
	 * @return
	 */
	
	@PutMapping
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) {
		
		String methodName = "updateCustomer()";
        logger.info(methodName + "called");
		
		Customer updatedCustomer = cust.updateCustomer(customer);
		if (updatedCustomer == null) {
			throw new InvaliddataException("Record Updated Successfully");
		}
		return new ResponseEntity<String>("Record Updation failed", HttpStatus.OK);
	}
	
	/**
	 * Handles a DELETE request to delete customer.
	 * @param id
	 * @return
	 */
	
    @DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") int id) {
	
		String methodName = " deleteCustomer()";
        logger.info(methodName + "called");
        
		String result = cust.deleteCustomer(id);

		if (result.equals("Record not found")) {
			throw new NotFoundException("No customer found for the provided ID: " + id);
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
    /**
	 * Handles a GET request to get CustomerBy EmailAddress.
	 * @param emailAddress
	 * @return
	 */

	@GetMapping("/{email}")
	public ResponseEntity<List<Customer>> getCustomersByEmailAddress(@PathVariable("email") String emailAddress) {
		
		String methodName = "getCustomersByEmailAddress()";
        logger.info(methodName + "called");
		
		List<Customer> customers = cust.getCustomerByEmailAddress(emailAddress);

		if (customers.isEmpty()) {
			throw new NotFoundException("No customers found with the provided email address");
		}
		return ResponseEntity.ok(customers);
	}
	
	/**
	 * Handles a GET request to get CustomerBy shipmentStatus.
	 * @param shipmentStatus
	 * @return
	 */
	

	@GetMapping("/shipment")
	public ResponseEntity<List<ShipmentStatusCountCustomer>> getOrderCountByStatus(
			@RequestParam("status") String shipmentStatus) {
		
		String methodName = "getOrderCountByStatus()";
        logger.info(methodName + "called");
		
		List<ShipmentStatusCountCustomer> shipmentStatusCount = cust.getOrderCountByStatus(shipmentStatus);

		if (shipmentStatusCount.isEmpty()) {
			throw new InvaliddataException("No shipment status count found for status: " + shipmentStatus);
		}
		return new ResponseEntity<List<ShipmentStatusCountCustomer>>(shipmentStatusCount, HttpStatus.OK);
	}
	
	/**
	 * Handles a GET request to get CustomerOrders
	 * @param customerId
	 * @return
	 */
	
    @GetMapping("/{custid}/order")
	public ResponseEntity<CustomerOrders> getCustomerOrders(@PathVariable("custid") int customerId) {
		
		String methodName = "getCustomerOrders()";
        logger.info(methodName + "called");
	
		CustomerOrders customerOrders = cust.getCustomerOrders(customerId);

		if (customerOrders.getCustomer() == null && customerOrders.getOrder().isEmpty()) {
			throw new NotFoundException("No customer orders found for the provided customer ID: " + customerId);
		}
		return ResponseEntity.ok(customerOrders);
	}
	
    /**
	 * Handles a GET request to get CustomerShipment.
	 * @param customerId
	 * @return
	 */
	
	@GetMapping("/{custid}/shipment")
	public ResponseEntity<CustomerShipment> getCustomerShipment(@PathVariable("custid") int customerId) {
		
		String methodName = "getCustomerShipment()";
        logger.info(methodName + "called");
		
		CustomerShipment customerShipment = cust.getCustomerShipment(customerId);

		if (customerShipment.getCustomer() == null && customerShipment.getShipment().isEmpty()) {
			throw new NotFoundException("No customer shipment found for the provided customer ID: " + customerId);
		}
		return ResponseEntity.ok(customerShipment);
	}

}
