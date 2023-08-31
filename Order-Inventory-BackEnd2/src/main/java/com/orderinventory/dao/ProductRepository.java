package com.orderinventory.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.orderinventory.entities.Product;

/*
 * @Repository annotation is used to indicate that a class serves as a repository or a data access object (DAO). 
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	public List<Product> findByProductName(String productName);
	
	public List<Product> findByUnitPriceBetween(BigDecimal min, BigDecimal max);
	
	public List<Product> findAll(Sort sort);
}
