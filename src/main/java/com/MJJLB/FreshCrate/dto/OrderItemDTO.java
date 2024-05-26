package com.MJJLB.FreshCrate.dto;

public class OrderItemDTO {
    private String name;
    private int quantity;

    public OrderItemDTO(String name, int quantity) {
        setName(name);
        setQuantity(quantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
