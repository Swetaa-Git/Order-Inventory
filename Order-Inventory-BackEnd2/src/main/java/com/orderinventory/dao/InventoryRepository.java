package com.orderinventory.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orderinventory.dto.InventoryProductCustomerStore;
import com.orderinventory.dto.InventoryProductStoreShipmentStatusSum;
import com.orderinventory.dto.InventoryShipment;
import com.orderinventory.dto.ShipmentStatusSoldProducts;
import com.orderinventory.entities.Inventory;

/*
 * @Repository annotation is used to indicate that a class serves as a repository or a data access object (DAO). 
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer>{
	/*
	 * @Query annotation represents a custom SQL query that retrieves specific data from the "Order" and "OrderItem" tables based on a specific order ID. 
	 */
	@Query("select new com.orderinventory.dto.InventoryProductCustomerStore(oi.product, o.customer, o.store) "
			+"from Order o join OrderItem oi "
			+"on o.orderId = oi.order.orderId "
			+"where o.orderId =:id")
	public InventoryProductCustomerStore findProductCustomerStoreByOrderId(@Param("id") int orderId);
	
	/*
	 * @Query annotation represents a custom SQL query that retrieves specific data from the "OrderItem" and "Order" tables based on a specific order ID.
	 */
	@Query("SELECT new com.orderinventory.dto.InventoryProductStoreShipmentStatusSum(oi.product, o.store, oi.shipment.shipmentStatus, SUM(oi.unitPrice * oi.quantity) AS total) " +
	        "FROM OrderItem oi " +
	        "JOIN oi.order o " +
	        "WHERE oi.order.orderId = :orderId " +
	        "GROUP BY oi.product")
	public List<InventoryProductStoreShipmentStatusSum> findInventoryDetailsByOrderId(@Param("orderId") int orderId);

/*
 * @Query annotation represents a custom SQL query that retrieves specific data from the "OrderItem" and "Order" tables based on a specific store ID.
 */
	@Query("SELECT DISTINCT oi.product, o.orderStatus " +
	        "FROM OrderItem oi " +
	        "JOIN oi.order o " +
	        "WHERE o.store.storeId = :storeId")
	public List<Object[]> findProductOrderStatusByStoreId(@Param("storeId") int storeId);
	
	/*
	 * @Query annotation represents a custom SQL query that retrieves specific data from the "Shipment" and "Inventory" tables based on a specific shipment ID.
	 */
	@Query("SELECT new com.orderinventory.dto.InventoryShipment(sp, i) FROM Shipment sp JOIN Inventory i on sp.store.storeId = i.store.storeId WHERE sp.shipmentId = :shipmentId")
	public InventoryShipment findInventoryShipmentById(@Param("shipmentId") int shipmentId);
	
	/*
	 * @Query annotation represents a custom SQL query that retrieves specific data related to shipped and delivered products.
	 */
	@Query("select new com.orderinventory.dto.ShipmentStatusSoldProducts('DELIVERED' as status, count(o.product)) from Shipment s join OrderItem o on s.shipmentId = o.shipment.shipmentId where s.shipmentStatus = 'DELIVERED'")
	public ShipmentStatusSoldProducts findSoldProductsByShipmentStatus();
}
