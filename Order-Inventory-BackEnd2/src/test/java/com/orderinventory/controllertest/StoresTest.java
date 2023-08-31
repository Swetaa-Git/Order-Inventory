package com.orderinventory.controllertest;

import com.orderinventory.controllers.StoreController;
import com.orderinventory.entities.Store;
import com.orderinventory.exceptions.NotFoundException;
import com.orderinventory.services.StoreServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class StoresTest {

    @Mock
    private StoreServiceImpl storeServices;

    @InjectMocks
    private StoreController storeController;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllStores() {
        List<Store> stores = new ArrayList<>();
        stores.add(new Store());

        when(storeServices.getAllStores()).thenReturn(stores);

        ResponseEntity<List<Store>> response = storeController.getAllStores();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stores, response.getBody());

        verify(storeServices, times(1)).getAllStores();
    }

    @Test(expected = NotFoundException.class)
    public void testGetAllStores_notFound() {
        when(storeServices.getAllStores()).thenReturn(null);

        storeController.getAllStores();
    }
}
