package com.orderinventory.controllertest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.orderinventory.controllers.CustomerController;
import com.orderinventory.dto.CustomerOrders;
import com.orderinventory.dto.CustomerShipment;
import com.orderinventory.dto.ShipmentStatusCountCustomer;
import com.orderinventory.entities.Customer;
import com.orderinventory.services.CustomerServiceImpl;

public class CustomersTest {

    @Mock
    private CustomerServiceImpl customerServices;

    @InjectMocks
    private CustomerController customerController;

    @Before(value = "")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCustomers_withFullName() {
        String fullName = "John Doe";
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());

        when(customerServices.getCustomersByFullName(fullName)).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers(fullName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());

        verify(customerServices, times(1)).getCustomersByFullName(fullName);
    }

    @Test
    public void testGetAllCustomers_withoutFullName() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());

        when(customerServices.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());

        verify(customerServices, times(1)).getAllCustomers();
    }

    @Test
    public void testGetAllCustomers_withoutFullName_notFound() {
        when(customerServices.getAllCustomers()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new ArrayList<>(), response.getBody());

        verify(customerServices, times(1)).getAllCustomers();
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer();
        when(customerServices.addCustomer(customer)).thenReturn(customer);

        ResponseEntity<String> response = customerController.addCustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Record Creation successfully", response.getBody());

        verify(customerServices, times(1)).addCustomer(customer);
    }



    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        when(customerServices.updateCustomer(customer)).thenReturn(customer);

        ResponseEntity<String> response = customerController.updateCustomer(customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record Updation failed", response.getBody());

        verify(customerServices, times(1)).updateCustomer(customer);
    }


    @Test
    public void testDeleteCustomer() {
        int customerId = 1;
        String result = "Record deleted successfully";
        when(customerServices.deleteCustomer(customerId)).thenReturn(result);

        ResponseEntity<String> response = customerController.deleteCustomer(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(result, response.getBody());

        verify(customerServices, times(1)).deleteCustomer(customerId);
    }



    @Test
    public void testGetCustomersByEmailAddress() {
        String emailAddress = "john@example.com";
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());

        when(customerServices.getCustomerByEmailAddress(emailAddress)).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getCustomersByEmailAddress(emailAddress);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());

        verify(customerServices, times(1)).getCustomerByEmailAddress(emailAddress);
    }

 

    @Test
    public void testGetOrderCountByStatus() {
        String shipmentStatus = "Delivered";
        List<ShipmentStatusCountCustomer> shipmentStatusCount = new ArrayList<>();
        shipmentStatusCount.add(new ShipmentStatusCountCustomer());

        when(customerServices.getOrderCountByStatus(shipmentStatus)).thenReturn(shipmentStatusCount);

        ResponseEntity<List<ShipmentStatusCountCustomer>> response = customerController.getOrderCountByStatus(shipmentStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shipmentStatusCount, response.getBody());

        verify(customerServices, times(1)).getOrderCountByStatus(shipmentStatus);
    }

 
    @Test
    public void testGetCustomerOrders() {
        int customerId = 1;
        CustomerOrders customerOrders = new CustomerOrders();
        customerOrders.setCustomer(new Customer());
        customerOrders.setOrder(new ArrayList<>());

        when(customerServices.getCustomerOrders(customerId)).thenReturn(customerOrders);

        ResponseEntity<CustomerOrders> response = customerController.getCustomerOrders(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerOrders, response.getBody());

        verify(customerServices, times(1)).getCustomerOrders(customerId);
    }



    @Test
    public void testGetCustomerShipment() {
        int customerId = 1;
        CustomerShipment customerShipment = new CustomerShipment();
        customerShipment.setCustomer(new Customer());
        customerShipment.setShipment(new ArrayList<>());

        when(customerServices.getCustomerShipment(customerId)).thenReturn(customerShipment);

        ResponseEntity<CustomerShipment> response = customerController.getCustomerShipment(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerShipment, response.getBody());

        verify(customerServices, times(1)).getCustomerShipment(customerId);
    }


}

