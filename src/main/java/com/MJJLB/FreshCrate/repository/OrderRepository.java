package com.MJJLB.FreshCrate.repository;

import com.MJJLB.FreshCrate.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository {

    List<OrderDetailDTO> getOrderDetails(Integer customerId,
                                          String orderStatus,
                                          Integer orderId,
                                          LocalDateTime orderDate,
                                          LocalDateTime deliveryDate);

    int createOrder(CreateOrderDTO createOrderDTO);

    void deleteOrder(int orderId);

    List<OrderItemDTO> getOrderItem(int orderId);

    void createOrderItem(int orderId, String name, int quantity);

    void updateOrderItem(UpdateOrderItemDTO updateOrderItemDTO);

    void deleteOrderItem(int orderId);

    String getOrderStatus(int orderId);

    List<OrderStatusHistoryDTO> getOrderStatusHistory(int orderId);

    void createOrderStatusHistory(int orderId, int statusId);

    void updateDeliveryDate(int orderId, LocalDateTime deliveryDate);

    void deleteOrderStatusHistory(int orderId);

    List<CustomerOrderHistoryDTO> getCustomerOrderHistory(int customerId, LocalDateTime startDate, LocalDateTime endDate);

    List<OrderSummaryReportDTO> getOrderSummaryReport(LocalDateTime startDate, LocalDateTime endDate);

    List<PendingOrderDTO> getPendingOrders(int maxDays);
}
