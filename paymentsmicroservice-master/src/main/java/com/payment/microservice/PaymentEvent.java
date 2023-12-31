package com.payment.microservice;

import com.payment.microservice.entity.CustomerOrder;

public class PaymentEvent {

    private String type;

    private CustomerOrder order;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public void setOrder(CustomerOrder order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "PaymentEvent [type=" + type + ", order=" + order + "]";
    }

}
