package com.MJJLB.FreshCrate.service.impl;

import com.MJJLB.FreshCrate.dto.*;
import com.MJJLB.FreshCrate.service.OrderService;
import com.MJJLB.FreshCrate.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDetailDTO> getOrderDetails(Integer customerId,
                                                String orderStatus,
                                                Integer orderId,
                                                LocalDateTime orderDate,
                                                LocalDateTime deliveryDate) {
        return orderRepository.getOrderDetails(customerId, orderStatus, orderId, orderDate, deliveryDate);
    }

    @Override
    @Transactional
    public void createOrder(CreateOrderDTO createOrderDTO) {
        int orderId = orderRepository.createOrder(createOrderDTO);
        for (OrderItemDTO item : createOrderDTO.getOrderItems()) {
            createOrderItem(orderId, item.getName(), item.getQuantity());
        }
        createOrderStatusHistory(orderId, 1);
    }

    @Override
    @Transactional
    public void deleteOrder(int orderId) {
        orderRepository.deleteOrderStatusHistory(orderId);
        orderRepository.deleteOrder(orderId);
    }

    @Override
    public List<OrderItemDTO> getOrderItem(int orderId) {
        return orderRepository.getOrderItem(orderId);
    }

    @Override
    public void createOrderItem(int orderId, String name, int quantity) {
        orderRepository.createOrderItem(orderId, name, quantity);
    }

    @Override
    public void updateOrderItem(UpdateOrderItemDTO updateOrderItemDTO) {
        orderRepository.updateOrderItem(updateOrderItemDTO);
    }

    @Override
    public void deleteOrderItem(int orderId) {
        orderRepository.deleteOrderItem(orderId);
    }

    @Override
    public String getOrderStatus(int orderId) {
        return orderRepository.getOrderStatus(orderId);
    }

    @Override
    public List<OrderStatusHistoryDTO> getOrderStatusHistory(int orderId) {
        return orderRepository.getOrderStatusHistory(orderId);
    }

    @Override
    @Transactional
    public void createOrderStatusHistory(int orderId, int statusId) {
        orderRepository.createOrderStatusHistory(orderId, statusId);
        if (statusId == 3) {
            LocalDateTime deliveryDate = LocalDateTime.now().plusDays(5);
            orderRepository.updateDeliveryDate(orderId, deliveryDate);
        } else if (statusId == 4) {
            LocalDateTime deliveryDate = LocalDateTime.now();
            orderRepository.updateDeliveryDate(orderId, deliveryDate);
        }
    }

    @Override
    public void deleteOrderStatusHistory(int orderId) {
        orderRepository.deleteOrderStatusHistory(orderId);
    }

    @Override
    @Transactional
    public List<CustomerOrderHistoryDTO> getCustomerOrderHistory(int customerId, LocalDateTime startDate, LocalDateTime endDate) {
        List<CustomerOrderHistoryDTO> orders = orderRepository.getCustomerOrderHistory(customerId, startDate, endDate);
        for (CustomerOrderHistoryDTO order : orders) {
            String status = getOrderStatus(order.getOrderId());
            order.setStatus(status);
        }
        return orders;
    }

    @Override
    public List<OrderSummaryReportDTO> getOrderSummaryReport(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.getOrderSummaryReport(startDate, endDate);
    }

    @Override
    @Transactional
    public void bulkUpdateOrderStatus(List<Integer> orderIds, int statusId) {
        for (Integer orderId : orderIds) {
            createOrderStatusHistory(orderId, statusId);
        }
    }

    @Override
    public List<PendingOrderDTO> getPendingOrders(int maxDays) {
        return orderRepository.getPendingOrders(maxDays);
    }
}
