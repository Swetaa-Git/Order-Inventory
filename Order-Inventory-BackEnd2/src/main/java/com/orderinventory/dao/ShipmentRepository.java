package com.orderinventory.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.orderinventory.entities.Shipment;

/*
 * @Repository annotation is used to indicate that a class serves as a repository or a data access object (DAO). 
 */
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer>{
	
	/*
	 * @Query annotation represents a custom SQL query that retrieves a specific column, shipmentId, from the "Shipment" table.
	 */
	
	@Query("select sp.shipmentId from Shipment sp")
	public List<Integer> findAllShipmentId();
}
