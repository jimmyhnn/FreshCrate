package com.MJJLB.FreshCrate.dto;

import java.util.List;

public class BulkUpdateOrderStatusDTO {
    private List<Integer> orderIds;
    private int statusId;

    public BulkUpdateOrderStatusDTO(List<Integer> orderIds, int statusId) {
        setOrderIds(orderIds);
        setStatusId(statusId);
    }

    public List<Integer> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Integer> orderIds) {
        this.orderIds = orderIds;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
