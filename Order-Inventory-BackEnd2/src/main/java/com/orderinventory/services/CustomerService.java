package com.orderinventory.services;

import java.util.List;

import com.orderinventory.dto.CustomerOrders;
import com.orderinventory.dto.CustomerShipment;
import com.orderinventory.dto.ShipmentStatusCountCustomer;
import com.orderinventory.entities.Customer;

public interface CustomerService {

	Customer addCustomer(Customer customer);

	Customer updateCustomer(Customer customer);

	String deleteCustomer(int id);

	List<Customer> getCustomerByEmailAddress(String emialAddress);

	List<Customer> getCustomersByFullName(String fullName);

	List<ShipmentStatusCountCustomer> getOrderCountByStatus(String shipmentStatus);

	CustomerOrders getCustomerOrders(int customerId);

	CustomerShipment getCustomerShipment(int customerId);

}
