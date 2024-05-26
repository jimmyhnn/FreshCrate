package com.MJJLB.FreshCrate.controller;

import com.MJJLB.FreshCrate.dto.CreateOrderDTO;
import com.MJJLB.FreshCrate.dto.OrderDetailDTO;
import com.MJJLB.FreshCrate.dto.UpdateOrderItemDTO;
import com.MJJLB.FreshCrate.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orderDetails")
    public List<OrderDetailDTO> getOrderDetails(
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) Integer orderId,
            @RequestParam(required = false) LocalDateTime orderDate,
            @RequestParam(required = false) LocalDateTime deliveryDate) {

        return orderService.getOrderDetails(customerId, orderStatus, orderId, orderDate, deliveryDate);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        orderService.createOrder(createOrderDTO);
        return ResponseEntity.ok("Order created successfully");
    }

    @PutMapping("/updateOrderItem")
    public ResponseEntity<String> updateOrderItem(@RequestBody UpdateOrderItemDTO updateOrderItemDTO) {
        orderService.updateOrderItem(updateOrderItemDTO);
        return ResponseEntity.ok("Order item updated successfully");
    }

    @DeleteMapping("/deleteOrderItem")
    public ResponseEntity<?> deleteOrderItem( @RequestParam(required = true) Integer id) {
        orderService.deleteOrderItem(id);
        return ResponseEntity.ok("Order item deleted successfully.");
    }
}
