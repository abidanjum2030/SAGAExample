package com.order.microservice.repository;

import com.order.microservice.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity,Long>{
    
}
