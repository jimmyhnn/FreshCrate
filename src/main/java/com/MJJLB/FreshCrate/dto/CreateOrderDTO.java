package com.MJJLB.FreshCrate.dto;

import java.util.List;

public class CreateOrderDTO {
    private Integer customerId;
    private List<OrderItemDTO> orderItems;

    public CreateOrderDTO(Integer customerId, List<OrderItemDTO> orderItems) {
        setCustomerId(customerId);
        setOrderItems(orderItems);
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
