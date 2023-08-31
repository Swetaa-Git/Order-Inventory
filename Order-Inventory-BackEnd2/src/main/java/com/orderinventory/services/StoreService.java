package com.orderinventory.services;

import java.util.List;

import org.slf4j.Logger;

import com.orderinventory.controllers.CustomerController;
import com.orderinventory.entities.Store;
import com.orderinventory.utility.GlobalLogger;

public interface StoreService {
	
	List<Store> getAllStores();

	String getWebAddressByStoreId(int storeId);

}
