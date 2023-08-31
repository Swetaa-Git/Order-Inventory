package com.orderinventory.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;

/*
 * A class represents Order.
 * @Entity annotation is used to mark a Java class as an entity that represents a table or collection in a database.
 * @Table annotation is used to specify the mapping details between an entity class and a database table. 
 */

@Entity
@Table(name = "orders")
public class Order {

	/*
	 * @Id annotation is used to mark a field or property as the primary key of an entity class.
	 *  It indicates that the annotated attribute uniquely identifies each instance of the entity in the database.
	 * @GeneratedValue annotation is used to specify the generation strategy for primary key values in JPA entities. 
	 * @Column annotation is used to customize the mapping of a field or property to a column in the database table. 
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "order_tms", nullable = false)
    private Timestamp orderTimestamp;

    /*
     * @ManyToOne annotation is used to define a many-to-one relationship between entities in JPA. 
     * It indicates that the referencing entity has a many-to-one association with the referenced entity.
     * @JoinColumn annotation is used to specify the mapping details of a join column in a database table for a many-to-one or one-to-one relationship. 
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_status", nullable = false, length = 45)
    private String orderStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    /**
     * Default constructor.
     */
    public Order() {
    }

    /**
     * Constructor to initialize an Order object.
     *
     * @param orderTimestamp The timestamp of the order.
     * @param customer       The customer who placed the order.
     * @param orderStatus    The status of the order.
     * @param store          The store where the order was placed.
     */
    public Order(Timestamp orderTimestamp, Customer customer, String orderStatus, Store store) {
        this.orderTimestamp = orderTimestamp;
        this.customer = customer;
        this.orderStatus = orderStatus;
        this.store = store;
    }
    
    /**
     * Constructor to initialize an Order object with all the fields.
     *
     * @param orderId		 The id of the order.
     * @param orderTimestamp The timestamp of the order.
     * @param customer       The customer who placed the order.
     * @param orderStatus    The status of the order.
     * @param store          The store where the order was placed.
     */
    public Order(int orderId,Timestamp orderTimestamp, Customer customer, String orderStatus, Store store) {
    	this.orderId = orderId;
        this.orderTimestamp = orderTimestamp;
        this.customer = customer;
        this.orderStatus = orderStatus;
        this.store = store;
    }

    /**
     * Retrieves the order ID.
     *
     * @return The order ID.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the order ID.
     *
     * @param orderId The order ID to set.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Retrieves the timestamp of the order.
     *
     * @return The order timestamp.
     */
    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }

    /**
     * Sets the timestamp of the order.
     *
     * @param orderTimestamp The order timestamp to set.
     */
    public void setOrderTimestamp(Timestamp orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    /**
     * Retrieves the customer who placed the order.
     *
     * @return The customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who placed the order.
     *
     * @param customer The customer to set.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Retrieves the status of the order.
     *
     * @return The order status.
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the status of the order.
     *
     * @param orderStatus The order status to set.
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Retrieves the store where the order was placed.
     *
     * @return The store.
     */
    public Store getStore() {
        return store;
    }

    /**
     * Sets the store where the order was placed.
     *
     * @param store The store to set.
     */
    public void setStore(Store store) {
        this.store = store;
    }
}

