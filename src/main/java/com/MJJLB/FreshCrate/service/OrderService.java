package com.MJJLB.FreshCrate.service;

import com.MJJLB.FreshCrate.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    List<OrderDetailDTO> getOrderDetails(Integer customerId,
                                         String orderStatus,
                                         Integer orderId,
                                         LocalDateTime orderDate,
                                         LocalDateTime deliveryDate);

    void createOrder(CreateOrderDTO createOrderDTO);

    void deleteOrder(int orderId);

    List<OrderItemDTO> getOrderItem(int orderId);

    void createOrderItem(int orderId, String name, int quantity);

    void updateOrderItem(UpdateOrderItemDTO updateOrderItemDTO);

    void deleteOrderItem(int orderId);

    String getOrderStatus(int orderId);

    List<OrderStatusHistoryDTO> getOrderStatusHistory(int orderId);

    void createOrderStatusHistory(int orderId, int statusId);

    void deleteOrderStatusHistory(int orderId);

    List<CustomerOrderHistoryDTO> getCustomerOrderHistory(int customerId, LocalDateTime startDate, LocalDateTime endDate);

    List<OrderSummaryReportDTO> getOrderSummaryReport(LocalDateTime startDate, LocalDateTime endDate);

    void bulkUpdateOrderStatus(List<Integer> orderIds, int statusId);

    List<PendingOrderDTO> getPendingOrders(int maxDays);
}