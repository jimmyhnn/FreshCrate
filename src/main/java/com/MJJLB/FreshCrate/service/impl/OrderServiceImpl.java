package com.MJJLB.FreshCrate.service.impl;

import com.MJJLB.FreshCrate.dto.CreateOrderDTO;
import com.MJJLB.FreshCrate.dto.UpdateOrderItemDTO;
import com.MJJLB.FreshCrate.service.OrderService;
import com.MJJLB.FreshCrate.repository.OrderRepository;
import com.MJJLB.FreshCrate.dto.OrderDetailDTO;
import org.springframework.stereotype.Service;
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
        return orderRepository.findOrderDetails(customerId, orderStatus, orderId, orderDate, deliveryDate);
    }

    @Override
    public void createOrder(CreateOrderDTO createOrderDTO) {
        orderRepository.createOrder(createOrderDTO);
    }

    @Override
    public void updateOrderItem(UpdateOrderItemDTO updateOrderItemDTO) {
        orderRepository.updateOrderItem(updateOrderItemDTO);
    }

    @Override
    public void deleteOrderItem(int orderId) {
        orderRepository.deleteOrderItem(orderId);
    }
}
