package com.MJJLB.FreshCrate.dto;

public class OrderSummaryReportDTO {
    private String firstName;
    private String lastName;
    private int orderId;
    private int totalItemsOrdered;

    public OrderSummaryReportDTO(String firstName, String lastName, int orderId, int totalItemsOrdered) {
        setFirstName(firstName);
        setLastName(lastName);
        setOrderId(orderId);
        setTotalItemsOrdered(totalItemsOrdered);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTotalItemsOrdered() {
        return totalItemsOrdered;
    }

    public void setTotalItemsOrdered(int totalItemsOrdered) {
        this.totalItemsOrdered = totalItemsOrdered;
    }
}
