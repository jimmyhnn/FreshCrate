package com.MJJLB.FreshCrate.controller;

import com.MJJLB.FreshCrate.dto.*;
import com.MJJLB.FreshCrate.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Tag(name = "Order Management", description = "APIs related to managing orders of customers")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getOrderDetails")
    @Operation(summary = "Retrieves a list of placed orders by various criteria. \n" +
            "This helps employees get details of each order and manage and track orders efficiently\n" +
            "based on their current status and the specified date range.")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetails(
            @Parameter(description = "ID of the customer") @RequestParam(required = false) Integer customerId,
            @Parameter(description = "Status of the order") @RequestParam(required = false) String orderStatus,
            @Parameter(description = "ID of the order") @RequestParam(required = false) Integer orderId,
            @Parameter(description = "Date the order was placed") @RequestParam(required = false) LocalDateTime orderDate,
            @Parameter(description = "Date the order was/is to be delivered") @RequestParam(required = false) LocalDateTime deliveryDate) {

        return ResponseEntity.ok(orderService.getOrderDetails(customerId, orderStatus, orderId, orderDate, deliveryDate));
    }

    @PostMapping("/createOrder")
    @Operation(summary = "Create a new order. This includes order items in the order")
    public ResponseEntity<String> createOrder(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the order to be created")
                                                  @RequestBody(required = true) CreateOrderDTO createOrderDTO) {
        orderService.createOrder(createOrderDTO);
        return ResponseEntity.ok("Order created successfully");
    }

    @DeleteMapping("/deleteOrder")
    @Operation(summary = "Delete an order")
    public ResponseEntity<String> deleteOrder(@Parameter(description = "ID of the order to be deleted") @RequestParam(required = true) int orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully.");
    }

    @GetMapping("/getOrderItem")
    @Operation(summary = "Get order items of a specific order")
    public ResponseEntity<List<OrderItemDTO>> getOrderItem(@Parameter(description = "ID of the order") @RequestParam(required = true) int orderId) {
        List<OrderItemDTO> orderItems = orderService.getOrderItem(orderId);
        return ResponseEntity.ok(orderItems);
    }

    @PostMapping("/createOrderItem")
    @Operation(summary = "Adds a new item to an existing order. \n" +
            "This supports order modification and managing changes to customer orders.")
    public ResponseEntity<String> createOrderItem(@Parameter(description = "ID of the order") @RequestParam(required = true) int orderId,
                                                  @Parameter(description = "Name of the item") @RequestParam(required = true) String name,
                                                  @Parameter(description = "Quantity of the item") @RequestParam(required = true) int quantity) {
        orderService.createOrderItem(orderId, name, quantity);
        return ResponseEntity.ok("Order item created successfully.");
    }

    @PutMapping("/updateOrderItem")
    @Operation(summary = "Update details of an item in an existing order. \n" +
            "This supports maintaining accurate order records and handling customer order change requests.")
    public ResponseEntity<String> updateOrderItem(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the order item to be updated")
                                                      @RequestBody(required = true) UpdateOrderItemDTO updateOrderItemDTO) {
        orderService.updateOrderItem(updateOrderItemDTO);
        return ResponseEntity.ok("Order item updated successfully");
    }

    @DeleteMapping("/deleteOrderItem")
    @Operation(summary = "Delete an order item from an order")
    public ResponseEntity<String> deleteOrderItem(@Parameter(description = "ID of the order item to be deleted") @RequestParam(required = true) int id) {
        orderService.deleteOrderItem(id);
        return ResponseEntity.ok("Order item deleted successfully.");
    }

    @GetMapping("/getOrderStatus")
    @Operation(summary = "Get the status of an order")
    public ResponseEntity<String> getOrderStatus(@Parameter(description = "ID of the order") @RequestParam(required = true) int orderId) {
        String status = orderService.getOrderStatus(orderId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/getOrderStatusHistory")
    @Operation(summary = "Get the status history of an order")
    public ResponseEntity<List<OrderStatusHistoryDTO>> getOrderStatusHistory(@Parameter(description = "ID of the order") @RequestParam(required = true) int orderId) {
        List<OrderStatusHistoryDTO> history = orderService.getOrderStatusHistory(orderId);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/createOrderStatusHistory")
    @Operation(summary = "Create a status history entry for an order.\n" +
            "This is the same as updating the status of an order.")
    public ResponseEntity<String> createOrderStatusHistory(@Parameter(description = "ID of the order") @RequestParam(required = true) int orderId,
                                                           @Parameter(description = "ID of the status") @RequestParam(required = true) int statusId) {
        orderService.createOrderStatusHistory(orderId, statusId);
        return ResponseEntity.ok("Order status history created successfully.");
    }

    @DeleteMapping("/deleteOrderStatusHistory")
    @Operation(summary = "Delete a status history entry for an order")
    public ResponseEntity<String> deleteOrderStatusHistory(@Parameter(description = "ID of the order") @RequestParam(required = true) int orderId) {
        orderService.deleteOrderStatusHistory(orderId);
        return ResponseEntity.ok("Order status history deleted successfully.");
    }

    @GetMapping("/getCustomerOrderHistory")
    @Operation(summary = "Retrieves the complete order history for a specific customer, filtered by a date range.\n" +
            "This provides employees with a comprehensive view of a customer’s past transactions for customer service purposes or business analysis.")
    public ResponseEntity<List<CustomerOrderHistoryDTO>> getCustomerOrderHistory(@Parameter(description = "ID of the customer") @RequestParam(required = true) int customerId,
                                                                                 @Parameter(description = "Start date of the order history period") @RequestParam(required = true) LocalDateTime startDate,
                                                                                 @Parameter(description = "End date of the order history period") @RequestParam(required = true) LocalDateTime endDate) {
        List<CustomerOrderHistoryDTO> history = orderService.getCustomerOrderHistory(customerId, startDate, endDate);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/getOrderSummaryReport")
    @Operation(summary = "Generates a summary report of orders placed within a specified date range. This provides insight into the number of orders and items ordered for business analysis.")
    public ResponseEntity<List<OrderSummaryReportDTO>> getOrderSummaryReport(@Parameter(description = "Start date of the report period") @RequestParam(required = true) LocalDateTime startDate,
                                                                             @Parameter(description = "End date of the report period") @RequestParam(required = true) LocalDateTime endDate) {
        List<OrderSummaryReportDTO> report = orderService.getOrderSummaryReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @PostMapping("/bulkUpdateOrderStatus")
    @Operation(summary = "Updates the status of single or multiple order(s). This handles bulk operations efficiently\n" +
            "when processing a large volume of orders at once.")
    public ResponseEntity<String> bulkUpdateOrderStatus(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the bulk update request. This includes ids of the orders and the status they need to be updated to")
                                                            @RequestBody(required = true) BulkUpdateOrderStatusDTO bulkUpdateOrderStatusDTO) {
        orderService.bulkUpdateOrderStatus(bulkUpdateOrderStatusDTO.getOrderIds(), bulkUpdateOrderStatusDTO.getStatusId());
        return ResponseEntity.ok("Order statuses updated successfully.");
    }

    @GetMapping("/getPendingOrders")
    @Operation(summary = "Retrieves a list of orders that are pending delivery within a specified number of days. This helps in prioritizing and scheduling deliveries on-time.")
    public ResponseEntity<List<PendingOrderDTO>> getPendingOrders(@Parameter(description = "Maximum number of days for pending orders") @RequestParam(required = true) int maxDays) {
        List<PendingOrderDTO> pendingOrders = orderService.getPendingOrders(maxDays);
        return ResponseEntity.ok(pendingOrders);
    }
}
