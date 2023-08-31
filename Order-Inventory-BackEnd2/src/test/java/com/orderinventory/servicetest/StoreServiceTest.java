package com.orderinventory.servicetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.orderinventory.dao.StoreRepository;
import com.orderinventory.entities.Store;
import com.orderinventory.services.StoreServiceImpl;

public class StoreServiceTest {

    @Mock
    private StoreRepository repository;

    @InjectMocks
    private StoreServiceImpl service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllStores() {
        // Arrange
        Store store1 = new Store();
        Store store2 = new Store();
        List<Store> stores = Arrays.asList(store1, store2);
        when(repository.findAll()).thenReturn(stores);

        // Act
        List<Store> result = service.getAllStores();

        // Assert
        assertEquals(stores, result);
        verify(repository).findAll();
    }

    @Test
    public void testGetWebAddressByStoreId() {
        // Arrange
        int storeId = 1;
        Store store = new Store();
        store.setWebAddress("www.example.com");

        when(repository.findById(storeId)).thenReturn(java.util.Optional.of(store));

        // Act
        String result = service.getWebAddressByStoreId(storeId);

        // Assert
        assertEquals("www.example.com", result);
        verify(repository).findById(storeId);
    }
}