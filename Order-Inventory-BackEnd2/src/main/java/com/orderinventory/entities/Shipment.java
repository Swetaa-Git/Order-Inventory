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
/*
* A class represents shipments.
* @Entity annotation is used to mark a Java class as an entity that represents a table or collection in a database.
* @Table annotation is used to specify the mapping details between an entity class and a database table. 
*/
@Entity
@Table(name = "shipments")
public class Shipment {

	/*
	 * @Id annotation is used to mark a field or property as the primary key of an entity class. 
	 * It indicates that the annotated attribute uniquely identifies each instance of the entity in the database.
	 * @GeneratedValue annotation is used to specify the generation strategy for primary key values in JPA entities. 
	 * @Column annotation is used to customize the mapping of a field or property to a column in the database table. 
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private int shipmentId;

    /*
     * @ManyToOne annotation is used to define a many-to-one relationship between entities in JPA. 
     * It indicates that the referencing entity has a many-to-one association with the referenced entity.
     * @JoinColumn annotation is used to specify the mapping details of a join column in a database table for a many-to-one or one-to-one relationship. 
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "delivery_address", nullable = false, length = 512)
    private String deliveryAddress;

    @Column(name = "shipment_status", nullable = false, length = 100)
    private String shipmentStatus;

    // Constructors, getters, and setters

    /**
     * Default constructor.
     */
    public Shipment() {
    }

    /**
     * Constructor to initialize a Shipment object.
     *
     * @param store           The store associated with the shipment.
     * @param customer        The customer associated with the shipment.
     * @param deliveryAddress The delivery address for the shipment.
     * @param shipmentStatus  The status of the shipment.
     */
    public Shipment(Store store, Customer customer, String deliveryAddress, String shipmentStatus) {
        this.store = store;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.shipmentStatus = shipmentStatus;
    }
    
    /**
     * Constructor to initialize a Shipment object with all the fields
     *
     * @param shipmentId	  The id associated with the shipment 
     * @param store           The store associated with the shipment.
     * @param customer        The customer associated with the shipment.
     * @param deliveryAddress The delivery address for the shipment.
     * @param shipmentStatus  The status of the shipment.
     */
    public Shipment(int shipmentId, Store store, Customer customer, String deliveryAddress, String shipmentStatus) {
    	this.shipmentId = shipmentId;
        this.store = store;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.shipmentStatus = shipmentStatus;
    }

    /**
     * Retrieves the shipment ID.
     *
     * @return The shipment ID.
     */
    public int getShipmentId() {
        return shipmentId;
    }

    /**
     * Sets the shipment ID.
     *
     * @param shipmentId The shipment ID to set.
     */
    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    /**
     * Retrieves the store associated with the shipment.
     *
     * @return The store associated with the shipment.
     */
    public Store getStore() {
        return store;
    }

    /**
     * Sets the store associated with the shipment.
     *
     * @param store The store to set.
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * Retrieves the customer associated with the shipment.
     *
     * @return The customer associated with the shipment.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with the shipment.
     *
     * @param customer The customer to set.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Retrieves the delivery address for the shipment.
     *
     * @return The delivery address.
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Sets the delivery address for the shipment.
     *
     * @param deliveryAddress The delivery address to set.
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Retrieves the status of the shipment.
     *
     * @return The shipment status.
     */
    public String getShipmentStatus() {
        return shipmentStatus;
    }

    /**
     * Sets the status of the shipment.
     *
     * @param shipmentStatus The shipment status to set.
     */
    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }
}

