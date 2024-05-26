package com.MJJLB.FreshCrate.service;

import com.MJJLB.FreshCrate.dto.CreateOrderDTO;
import com.MJJLB.FreshCrate.dto.OrderDetailDTO;
import com.MJJLB.FreshCrate.dto.UpdateOrderItemDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    List<OrderDetailDTO> getOrderDetails(Integer customerId,
                                         String orderStatus,
                                         Integer orderId,
                                         LocalDateTime orderDate,
                                         LocalDateTime deliveryDate);

    void createOrder(CreateOrderDTO createOrderDTO);

    void updateOrderItem(UpdateOrderItemDTO updateOrderItemDTO);

    void deleteOrderItem(int orderId);
}
