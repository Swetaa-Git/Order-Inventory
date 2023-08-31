package com.orderinventory.services;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderinventory.controllers.CustomerController;
import com.orderinventory.dao.CustomerRepository;
import com.orderinventory.dto.CustomerOrders;
import com.orderinventory.dto.CustomerShipment;
import com.orderinventory.dto.ShipmentStatusCountCustomer;
import com.orderinventory.entities.Customer;
import com.orderinventory.entities.Order;
import com.orderinventory.entities.Shipment;
import com.orderinventory.utility.GlobalLogger;

/**
 * @Service  is used to mark a class as a service component.
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService{


	private Logger logger = GlobalLogger.getLogger(CustomerController.class);

	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	
	@Autowired
	private CustomerRepository repo;
	
	public List<Customer> getAllCustomers(){
		String methodName = "getAllCustomers()";
		logger.info(methodName + "called");
		return repo.findAll();
	}


	/**
	 * @Override informs the compiler that the element is meant to override an element declared in a superclass
	 */
	
	@Override
	public Customer addCustomer(Customer customer) {
		String methodName = "addCustomer()";
		logger.info(methodName + "called");
		return repo.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		String methodName = "updateCustomer()";
		logger.info(methodName + "called");
		Customer cust = repo.findById(customer.getCustomerId()).orElse(null);
		cust.setEmailAddress(customer.getEmailAddress());
		cust.setFullName(customer.getFullName());
		return repo.save(cust);
	}
	
	@Override
	public String deleteCustomer(int id) {
		String methodName = "deleteCustomer()";
		logger.info(methodName + "called");
		if (repo.existsById(id)) {
			repo.deleteById(id);
			return "Record deleted Successfully";
		}
		return "Deletion Failed";
	}
	
	@Override
	public List<Customer> getCustomerByEmailAddress(String emialAddress){
		String methodName = "getCustomerByEmailAddress()";
		logger.info(methodName + "called");
		return repo.findByEmailAddress(emialAddress);
	}
	
	@Override
	public List<Customer> getCustomersByFullName(String fullName){
		String methodName = "getCustomersByFullName()";
		logger.info(methodName + "called");
		return repo.findByFullNameContains(fullName);
	}
	
	@Override
	public List<ShipmentStatusCountCustomer> getOrderCountByStatus(String shipmentStatus){
		String methodName = "getOrderCountByStatus()";
		logger.info(methodName + "called");
		return repo.getOrderCountByStatus(shipmentStatus);
	}
	
	@Override
	public CustomerOrders getCustomerOrders(int customerId){
		String methodName = "getCustomerOrders()";
		logger.info(methodName + "called");
		Customer customer = repo.findById(customerId).orElse(null);
		List<Order> order = repo.getCustomerOrders(customerId);
		return new CustomerOrders(customer, order);
	}
	
	@Override
	public CustomerShipment getCustomerShipment(int customerId){
		String methodName = "getCustomerShipment()";
		logger.info(methodName + "called");
		Customer customer = repo.findById(customerId).orElse(null);
		List<Shipment> shipment = repo.getCustomerShipments(customerId);
		return new CustomerShipment(customer, shipment);
	}
}
