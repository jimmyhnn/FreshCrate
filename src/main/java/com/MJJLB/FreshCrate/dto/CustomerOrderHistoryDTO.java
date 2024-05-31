package com.MJJLB.FreshCrate.dto;

import java.time.LocalDateTime;

public class CustomerOrderHistoryDTO {
    private int orderId;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private String status;

    public CustomerOrderHistoryDTO(int orderId,
                                   LocalDateTime orderDate,
                                   LocalDateTime deliveryDate,
                                   String status) {
        setOrderId(orderId);
        setOrderDate(orderDate);
        setDeliveryDate(deliveryDate);
        setStatus(status);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
