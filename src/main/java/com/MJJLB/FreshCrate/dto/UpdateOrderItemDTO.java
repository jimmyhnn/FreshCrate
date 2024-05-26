package com.MJJLB.FreshCrate.dto;

public class UpdateOrderItemDTO {
    private Integer orderId;
    private String orderName;
    private Integer orderQuantity;

    public UpdateOrderItemDTO(Integer orderId, String orderName, Integer orderQuantity) {
        setOrderId(orderId);
        setOrderName(orderName);
        setOrderQuantity(orderQuantity);
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
