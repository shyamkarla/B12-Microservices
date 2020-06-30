package com.b12.inventory.inventoryService.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.b12.inventory.inventoryService.model.Inventory;


@Repository
public interface InventoryRepository extends CrudRepository<Inventory,Integer>{
	
	public Inventory findByProductName(String name);
	
	public List<Inventory> findByProductGroup(String group);
	
	public List<Inventory> findByProductType(String type);

	@Query(value ="SELECT * FROM INVENTORY  WHERE product_name=?1 ",
	nativeQuery = true)
	public Inventory productAvailabilityNotification(String name);

	@Query(value ="SELECT * FROM INVENTORY  WHERE product_name=?1 and is_available='Y'and quantity >0",nativeQuery = true)
	public Inventory findProductAvailable(String name);
}
