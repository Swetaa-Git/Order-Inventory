package com.orderinventory.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orderinventory.dto.ShipmentStatusCountCustomer;
import com.orderinventory.entities.Customer;
import com.orderinventory.entities.Order;
import com.orderinventory.entities.Shipment;

/*
 * @Repository annotation is used to indicate that a class serves as a repository or a data access object (DAO). 
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	public List<Customer> findByEmailAddress(String emailAddress);
	public List<Customer> findByFullNameContains(String fullName);
	/*
	 * @Query annotation represents a custom SQL query that retrieves data from the "Shipment" table and constructs instances of the ShipmentStatusCountCustomer class. 
	 */
	@Query("SELECT new com.orderinventory.dto.ShipmentStatusCountCustomer(s.shipmentStatus, COUNT(s.customer)) " +
		       "FROM Shipment s " +
		       "WHERE s.shipmentStatus = :status " +
		       "GROUP BY s.shipmentStatus")
	public List<ShipmentStatusCountCustomer> getOrderCountByStatus(@Param("status") String shipmentStatus);
	/*
	 * @Query annotation represents a custom SQL query that retrieves data from the "Order" table based on a specific customer ID. 
	 */
	@Query("SELECT o " +
		       "FROM Customer c, Order o " +
		       "WHERE c.customerId = :Id " +
		       "AND c.customerId = o.customer.customerId")
	public List<Order> getCustomerOrders(@Param("Id") int customerId);

	/*
	 * @Query annotation represents a custom SQL query that retrieves data from the "Shipment" table based on a specific customer ID.
	 */
	@Query("SELECT s FROM Shipment s " +
		       "WHERE s.customer.customerId = :Id ")
	public List<Shipment> getCustomerShipments(@Param("Id") int customerId);
}
