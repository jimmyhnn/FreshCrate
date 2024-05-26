package com.MJJLB.FreshCrate.dto;

import java.time.LocalDateTime;

public class OrderDetailDTO {
    private String customerFirstName;
    private String customerLastName;
    private Integer orderId;
    private String orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;

    public OrderDetailDTO(String customerFirstName,
                          String customerLastName,
                          Integer orderId,
                          String orderStatus,
                          LocalDateTime orderDate,
                          LocalDateTime deliveryDate) {
        setCustomerFirstName(customerFirstName);
        setCustomerLastName(customerLastName);
        setOrderId(orderId);
        setOrderStatus(orderStatus);
        setOrderDate(orderDate);
        setDeliveryDate(deliveryDate);
    }
    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
}
