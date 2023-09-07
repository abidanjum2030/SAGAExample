package com.payment.microservice;

import com.payment.microservice.entity.CustomerOrder;
import com.payment.microservice.entity.Payment;
import com.payment.microservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReversePayment {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @KafkaListener(topics = "reversed-payments", groupId = "payments-group")
    public void reversePayment(String event) {

        try {

            PaymentEvent paymentEvent = new ObjectMapper().readValue(event, PaymentEvent.class);

            CustomerOrder order = paymentEvent.getOrder();

            // do refund..

            // update status as failed

            Iterable<Payment> payments = this.repository.findByOrderId(order.getOrderId());

            payments.forEach(p -> {

                p.setStatus("FAILED");
                this.repository.save(p);
            });

            // reverse previous task
            OrderEvent orderEvent = new OrderEvent();
            orderEvent.setOrder(paymentEvent.getOrder());
            orderEvent.setType("ORDER_REVERSED");
            this.kafkaTemplate.send("reversed-orders", orderEvent);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
