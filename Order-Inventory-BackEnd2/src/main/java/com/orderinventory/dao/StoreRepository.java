package com.orderinventory.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderinventory.entities.Store;

 /*
 * @Repository annotation is used to indicate that a class serves as a repository or a data access object (DAO). 
 */
 
@Repository
public interface StoreRepository extends JpaRepository<Store, Integer>{

}
