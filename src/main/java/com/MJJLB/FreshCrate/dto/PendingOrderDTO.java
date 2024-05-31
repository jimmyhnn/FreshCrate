package com.MJJLB.FreshCrate.dto;

public class PendingOrderDTO {
    private Integer orderId;
    private String firstName;
    private String lastName;
    private int daysPending;

    // Constructor
    public PendingOrderDTO(Integer orderId, String firstName, String lastName, int daysPending) {
        setOrderId(orderId);
        setFirstName(firstName);
        setLastName(lastName);
        setDaysPending(daysPending);
    }

    // Getters and Setters
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public int getDaysPending() {
        return daysPending;
    }

    public void setDaysPending(int daysPending) {
        this.daysPending = daysPending;
    }
}

