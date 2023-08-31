package com.orderinventory.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.orderinventory.dto.OrderStatusCount;
import com.orderinventory.dto.OrderStoreList;
import com.orderinventory.entities.Order;

/*
 * @Repository annotation is used to indicate that a class serves as a repository or a data access object (DAO). 
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	/*
	 * @Query annotation represents a custom SQL query that retrieves specific data from the "Order" and "Store" tables based on a specific store ID. 
	 */
	@Query("SELECT new com.orderinventory.dto.OrderStoreList(o.orderId, o.orderStatus, s.storeName, s.webAddress) " +
		       "FROM Order o JOIN o.store s " +
		       "WHERE s.storeId = :id")
	public List<OrderStoreList> findByStoreId(@Param("id") int id);
	
	/*
	 * @Query annotation represents a custom SQL query that retrieves specific data from the "Order" table based on a specific order status.
	 */
	@Query("SELECT  new com.orderinventory.dto.OrderStatusCount(o.orderStatus, COUNT(o.orderId)) " +
		       "FROM Order o " +
		       "WHERE o.orderStatus = :status " +
		       "GROUP BY o.orderStatus")
	public List<OrderStatusCount> getOrderCountByStatus(@Param("status") String orderStatus);
}
