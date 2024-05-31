package com.MJJLB.FreshCrate.dto;

import java.time.LocalDateTime;

public class OrderStatusHistoryDTO {
    private String status;
    private LocalDateTime changeDate;

    public OrderStatusHistoryDTO(String status, LocalDateTime changeDate) {
        setStatus(status);
        setChangeDate(changeDate);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDateTime changeDate) {
        this.changeDate = changeDate;
    }
}
