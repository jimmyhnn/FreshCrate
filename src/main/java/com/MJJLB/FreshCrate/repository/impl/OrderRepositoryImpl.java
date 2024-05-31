package com.MJJLB.FreshCrate.repository.impl;

import com.MJJLB.FreshCrate.dto.*;
import com.MJJLB.FreshCrate.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<OrderDetailDTO> getOrderDetails(Integer customerId,
                                                 String orderStatus,
                                                 Integer orderId,
                                                 LocalDateTime orderDate,
                                                 LocalDateTime deliveryDate) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT customer.firstName, customer.lastName, orderItem.id AS order_id, " +
                "orderStatus.status, customerOrder.orderDate, customerOrder.deliveryDate " +
                "FROM customer " +
                "JOIN CustomerOrder ON (customer.id = CustomerOrder.customerId) " +
                "JOIN orderItem ON (CustomerOrder.id = orderItem.orderId) " +
                "JOIN orderStatusHistory ON (CustomerOrder.id = orderStatusHistory.orderId) " +
                "JOIN orderStatus ON (orderStatusHistory.statusId = orderStatus.id) " +
                "WHERE 1=1 ");

        if (customerId != null) {
            sql.append(" AND customer.id = ? ");
            params.add(customerId);
        }
        if (orderStatus != null) {
            sql.append(" AND orderStatus.status = ? ");
            params.add(orderStatus);
        }
        if (orderId != null) {
            sql.append(" AND orderItem.id = ? ");
            params.add(orderId);
        }
        if (orderDate != null) {
            sql.append(" AND customerOrder.orderDate = ? ");
            params.add(orderDate);
        }
        if (deliveryDate != null) {
            sql.append(" AND customerOrder.deliveryDate = ? ");
            params.add(deliveryDate);
        }
        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new OrderDetailDTO(
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getInt("order_id"),
                rs.getString("status"),
                rs.getObject("orderdate", LocalDateTime.class),
                rs.getObject("deliverydate", LocalDateTime.class)
        ));
    }

    @Override
    public int createOrder(CreateOrderDTO createOrderDTO) {
        final String sql = "INSERT INTO CustomerOrder (customerId, orderDate) VALUES (?, NOW())";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, createOrderDTO.getCustomerId());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("Failed to insert order. No ID obtained from CustomerOrderTable");
        }
        return key.intValue();
    }

    @Override
    public void deleteOrder(int orderId) {
        String deleteOrder = "DELETE FROM CustomerOrder WHERE id = ?";
        String deleteAllOrderItems = "DELETE FROM OrderItem WHERE orderId = ?";
        jdbcTemplate.update(deleteAllOrderItems, orderId);
        jdbcTemplate.update(deleteOrder, orderId);
    }

    @Override
    public List<OrderItemDTO> getOrderItem(int orderId) {
        String sql = "SELECT name, quantity FROM OrderItem WHERE orderId = ?";
        return jdbcTemplate.query(sql, new Object[]{orderId},
                (rs, rowNum) -> new OrderItemDTO(rs.getString("name"), rs.getInt("quantity")));
    }

    @Override
    public void createOrderItem(int orderId, String name, int quantity) {
        String sql = "INSERT INTO OrderItem (orderId, name, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, orderId, name, quantity);
    }

    @Override
    public void updateOrderItem(UpdateOrderItemDTO updateOrderItemDTO) {
        final String sql = "UPDATE OrderItem SET name = ?, quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, updateOrderItemDTO.getOrderName(), updateOrderItemDTO.getOrderQuantity(), updateOrderItemDTO.getOrderId());
    }

    @Override
    public void deleteOrderItem(int orderId) {
        String sql = "DELETE FROM OrderItem WHERE id = ?";
        jdbcTemplate.update(sql, orderId);
    }

    @Override
    public String getOrderStatus(int orderId) {
        String sql = "SELECT status FROM OrderStatus JOIN OrderStatusHistory ON (OrderStatusHistory.statusid = OrderStatus.id) WHERE orderId = ? ORDER BY changeDate DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new Object[]{orderId}, String.class);
    }

    @Override
    public List<OrderStatusHistoryDTO> getOrderStatusHistory(int orderId) {
        String sql = "SELECT OrderStatus.status, OrderStatusHistory.changeDate FROM OrderStatus " +
                "JOIN OrderStatusHistory ON (OrderStatusHistory.statusId = OrderStatus.id) " +
                "WHERE orderId = ? ORDER BY changeDate DESC";
        return jdbcTemplate.query(sql, new Object[]{orderId}, (rs, rowNum) -> new OrderStatusHistoryDTO(
                rs.getString("status"),
                rs.getTimestamp("changeDate").toLocalDateTime()
        ));
    }

    @Override
    public void createOrderStatusHistory(int orderId, int statusId) {
        String sql = "INSERT INTO OrderStatusHistory (orderId, statusId, changeDate) VALUES (?, ?, NOW())";
        jdbcTemplate.update(sql, orderId, statusId);
    }

    @Override
    public void updateDeliveryDate(int orderId, LocalDateTime deliveryDate) {
        String sql = "UPDATE CustomerOrder SET deliveryDate = ? WHERE id = ?";
        jdbcTemplate.update(sql, deliveryDate, orderId);
    }

    @Override
    public void deleteOrderStatusHistory(int orderId) {
        String sql = "DELETE FROM OrderStatusHistory WHERE orderId = ?";
        jdbcTemplate.update(sql, orderId);
    }

    @Override
    public List<CustomerOrderHistoryDTO> getCustomerOrderHistory(int customerId, LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT CustomerOrder.id, CustomerOrder.orderDate, CustomerOrder.deliveryDate " +
                "FROM Customer JOIN CustomerOrder ON (Customer.id = CustomerOrder.customerId) " +
                "WHERE Customer.id = ? AND CustomerOrder.orderDate BETWEEN ? AND ?";
        List<CustomerOrderHistoryDTO> orders = jdbcTemplate.query(sql, new Object[]{customerId, startDate, endDate},
                (rs, rowNum) -> {
                    int orderId = rs.getInt("id");
                    LocalDateTime orderDate = toLocalDateTime(rs.getTimestamp("orderDate"));
                    LocalDateTime deliveryDate = toLocalDateTime(rs.getTimestamp("deliveryDate"));
                    return new CustomerOrderHistoryDTO(orderId, orderDate, deliveryDate, null);
                });
        return orders;
    }

    @Override
    public List<OrderSummaryReportDTO> getOrderSummaryReport(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT Customer.firstName, Customer.lastName, CustomerOrder.id AS orderid, COUNT(OrderItem.id) AS totalItemsOrdered " +
                "FROM Customer " +
                "JOIN CustomerOrder ON Customer.id = CustomerOrder.customerId " +
                "LEFT JOIN OrderItem ON CustomerOrder.id = OrderItem.orderId " +
                "WHERE CustomerOrder.orderDate BETWEEN ? AND ? " +
                "GROUP BY Customer.firstName, Customer.lastName, CustomerOrder.id " +
                "ORDER BY CustomerOrder.id";
        return jdbcTemplate.query(sql, new Object[]{startDate, endDate}, (rs, rowNum) ->
                new OrderSummaryReportDTO(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("id"),
                        rs.getInt("totalItemsOrdered")
                )
        );
    }

    @Override
    public List<PendingOrderDTO> getPendingOrders(int maxDays) {
        LocalDateTime minDate = LocalDateTime.now().minusDays(maxDays);
        String sql = "SELECT CustomerOrder.id AS orderId, Customer.firstName, Customer.lastName, OrderStatusHistory.changeDate " +
                "FROM CustomerOrder " +
                "JOIN Customer ON Customer.id = CustomerOrder.customerId " +
                "JOIN OrderStatusHistory ON CustomerOrder.id = OrderStatusHistory.orderId " +
                "WHERE OrderStatusHistory.statusId = 1 AND OrderStatusHistory.changeDate <= ? " +
                "GROUP BY CustomerOrder.id, Customer.firstName, Customer.lastName, OrderStatusHistory.changeDate " +
                "ORDER BY CustomerOrder.id";

        return jdbcTemplate.query(sql, new Object[]{minDate}, (rs, rowNum) -> {
            LocalDateTime changeDate = rs.getObject("changeDate", LocalDateTime.class);
            long daysPending = ChronoUnit.DAYS.between(changeDate, LocalDateTime.now());
            return new PendingOrderDTO(
                    rs.getInt("orderId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    (int) daysPending
            );
        });
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return (timestamp != null) ? timestamp.toLocalDateTime() : null;
    }

}
