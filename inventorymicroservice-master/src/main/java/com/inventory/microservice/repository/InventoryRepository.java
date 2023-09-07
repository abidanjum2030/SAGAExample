package com.inventory.microservice.repository;

import com.inventory.microservice.entity.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory,Long>{


    Iterable<Inventory> findByItem(String item);
}
