package com.MJJLB.FreshCrate.repository.impl;

import com.MJJLB.FreshCrate.dto.CreateOrderDTO;
import com.MJJLB.FreshCrate.dto.OrderDetailDTO;
import com.MJJLB.FreshCrate.dto.UpdateOrderItemDTO;
import com.MJJLB.FreshCrate.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
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
    public List<OrderDetailDTO> findOrderDetails(Integer customerId,
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
        return jdbcTemplate.query(sql.toString(), params.toArray(), orderDetailDtoRowMapper());
    }

    @Override
    @Transactional
    public void createOrder(CreateOrderDTO createOrderDTO) {
        // Insert the customer order and get the generated order ID
        final String insertOrderSql = "INSERT INTO CustomerOrder(customerId, orderDate) VALUES (?, NOW())";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertOrderSql, new String[]{"id"});
            ps.setInt(1, createOrderDTO.getCustomerId());
            return ps;
        }, keyHolder);

        // Retrieve the newly generated order ID
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("Failed to insert order. No id from CustomerOrderTable");
        }
        int orderId = key.intValue();

        // Insert order items using the new order ID
        createOrderDTO.getOrderItems().forEach(item -> {
            String insertItemSql = "INSERT INTO OrderItem (orderId, name, quantity) VALUES (?, ?, ?)";
            jdbcTemplate.update(insertItemSql, orderId, item.getName(), item.getQuantity());
        });

        // Insert into OrderStatusHistory
        final String insertStatusHistorySql = "INSERT INTO OrderStatusHistory (orderId, statusId, changedate) VALUES (?, 1, NOW())";
        jdbcTemplate.update(insertStatusHistorySql, orderId);
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

    private RowMapper<OrderDetailDTO> orderDetailDtoRowMapper() {
        return (rs, rowNum) -> new OrderDetailDTO(
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getInt("order_id"),
                rs.getString("status"),
                rs.getObject("orderdate", LocalDateTime.class),
                rs.getObject("deliverydate", LocalDateTime.class)
        );
    }
}
