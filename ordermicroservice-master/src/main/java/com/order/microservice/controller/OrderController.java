package com.order.microservice.controller;

import com.order.microservice.entity.CustomerOrder;
import com.order.microservice.event.OrderEvent;
import com.order.microservice.entity.OrderEntity;
import com.order.microservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping("/orders")
    public void createOrder(@RequestBody CustomerOrder customerOrder) {

        OrderEntity order = new OrderEntity();
        try {
            // save order in database

            order.setAmount(customerOrder.getAmount());
            order.setItem(customerOrder.getItem());
            order.setQuantity(customerOrder.getQuantity());
            order.setStatus("CREATED");
            order = this.repository.save(order);

            customerOrder.setOrderId(order.getId());

            // publish order created event for payment microservice to consume.

            OrderEvent event = new OrderEvent();
            event.setOrder(customerOrder);
            event.setType("ORDER_CREATED");
            this.kafkaTemplate.send("new-orders", event);
        } catch (Exception e) {

            order.setStatus("FAILED");
            this.repository.save(order);

        }

    }

}
