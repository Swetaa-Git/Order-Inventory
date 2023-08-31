package com.orderinventory.services;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderinventory.controllers.CustomerController;
import com.orderinventory.dao.StoreRepository;
import com.orderinventory.entities.Store;
import com.orderinventory.utility.GlobalLogger;

/**
 * @Service  is used to mark a class as a service component.
 */


@Service
public class StoreServiceImpl implements StoreService{
	
	private Logger logger = GlobalLogger.getLogger(CustomerController.class);
	
	/**
	 * @Autowired is used for automatic dependency injection in Spring Boot.
	 * It allows spring to automatically wire beans & dependencies.
	 */
	@Autowired
	private StoreRepository repo;
	
	/**
	 * @Override informs the compiler that the element is meant to override an element declared in a superclass
	 */
	@Override
	public List<Store> getAllStores(){
		String methodName = "getAllStores()";
        logger.info(methodName + "called");
		return repo.findAll();
	}
	@Override
	public String getWebAddressByStoreId(int storeId){
		String methodName = "getWebAddressByStoreId()";
        logger.info(methodName + "called");
		String webAddress = repo.findById(storeId).get().getWebAddress();
		return webAddress;
	}
}
