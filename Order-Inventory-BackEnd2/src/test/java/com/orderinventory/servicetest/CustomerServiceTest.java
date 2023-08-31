package com.orderinventory.servicetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.orderinventory.dao.CustomerRepository;
import com.orderinventory.dto.CustomerOrders;
import com.orderinventory.dto.CustomerShipment;
import com.orderinventory.dto.ShipmentStatusCountCustomer;
import com.orderinventory.entities.Customer;
import com.orderinventory.entities.Order;
import com.orderinventory.entities.Shipment;
import com.orderinventory.services.CustomerServiceImpl;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCustomers() {
        // Arrange
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(repository.findAll()).thenReturn(customers);

        // Act
        List<Customer> result = service.getAllCustomers();

        // Assert
        assertEquals(customers, result);
        verify(repository).findAll();
    }

    @Test
    public void testAddCustomer() {
        // Arrange
        Customer customer = new Customer();
        when(repository.save(customer)).thenReturn(customer);

        // Act
        Customer result = service.addCustomer(customer);

        // Assert
        assertEquals(customer, result);
        verify(repository).save(customer);
    }

    @Test
    public void testUpdateCustomer() {
        // Arrange
        int customerId = 1;
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(customerId);
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(customerId);
        updatedCustomer.setFullName("John Doe");
        updatedCustomer.setEmailAddress("john.doe@example.com");

        when(repository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(repository.save(updatedCustomer)).thenReturn(updatedCustomer);

        // Act
        Customer result = service.updateCustomer(updatedCustomer);

        // Assert
        assertEquals(updatedCustomer, result);
        assertEquals("John Doe", existingCustomer.getFullName());
        assertEquals("john.doe@example.com", existingCustomer.getEmailAddress());
        verify(repository).findById(customerId);
        verify(repository).save(updatedCustomer);
    }

    @Test
    public void testDeleteCustomerExists() {
        // Arrange
        int customerId = 1;
        when(repository.existsById(customerId)).thenReturn(true);

        // Act
        String result = service.deleteCustomer(customerId);

        // Assert
        assertEquals("Record deleted Successfully", result);
        verify(repository).existsById(customerId);
        verify(repository).deleteById(customerId);
    }

    @Test
    public void testDeleteCustomerNotExists() {
        // Arrange
        int customerId = 1;
        when(repository.existsById(customerId)).thenReturn(false);

        // Act
        String result = service.deleteCustomer(customerId);

        // Assert
        assertEquals("Deletion Failed", result);
        verify(repository).existsById(customerId);
        verify(repository, never()).deleteById(customerId);
    }

    @Test
    public void testGetCustomerByEmailAddress() {
        // Arrange
        String emailAddress = "john.doe@example.com";
        Customer customer = new Customer();
        when(repository.findByEmailAddress(emailAddress)).thenReturn(Arrays.asList(customer));

        // Act
        List<Customer> result = service.getCustomerByEmailAddress(emailAddress);

        // Assert
        assertEquals(Arrays.asList(customer), result);
        verify(repository).findByEmailAddress(emailAddress);
    }

    @Test
    public void testGetCustomersByFullName() {
        // Arrange
        String fullName = "John Doe";
        Customer customer = new Customer();
        when(repository.findByFullNameContains(fullName)).thenReturn(Arrays.asList(customer));

        // Act
        List<Customer> result = service.getCustomersByFullName(fullName);

        // Assert
        assertEquals(Arrays.asList(customer), result);
        verify(repository).findByFullNameContains(fullName);
    }

    @Test
    public void testGetOrderCountByStatus() {
        // Arrange
        String shipmentStatus = "Shipped";
        ShipmentStatusCountCustomer count = new ShipmentStatusCountCustomer();
        when(repository.getOrderCountByStatus(shipmentStatus)).thenReturn(Arrays.asList(count));

        // Act
        List<ShipmentStatusCountCustomer> result = service.getOrderCountByStatus(shipmentStatus);

        // Assert
        assertEquals(Arrays.asList(count), result);
        verify(repository).getOrderCountByStatus(shipmentStatus);
    }

    @Test
    public void testGetCustomerOrders() {
        // Arrange
        int customerId = 1;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orders = Arrays.asList(order1, order2);

        when(repository.findById(customerId)).thenReturn(Optional.of(customer));
        when(repository.getCustomerOrders(customerId)).thenReturn(orders);

        // Act
        CustomerOrders result = service.getCustomerOrders(customerId);

        // Assert
        assertEquals(customer, result.getCustomer());
        assertEquals(orders, result.getOrder());
        verify(repository).findById(customerId);
        verify(repository).getCustomerOrders(customerId);
    }

    @Test
    public void testGetCustomerShipment() {
        // Arrange
        int customerId = 1;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        Shipment shipment1 = new Shipment();
        Shipment shipment2 = new Shipment();
        List<Shipment> shipments = Arrays.asList(shipment1, shipment2);

        when(repository.findById(customerId)).thenReturn(Optional.of(customer));
        when(repository.getCustomerShipments(customerId)).thenReturn(shipments);

        // Act
        CustomerShipment result = service.getCustomerShipment(customerId);

        // Assert
        assertEquals(customer, result.getCustomer());
        assertEquals(shipments, result.getShipment());
        verify(repository).findById(customerId);
        verify(repository).getCustomerShipments(customerId);
    }
}
