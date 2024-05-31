package com.MJJLB.FreshCrate.repository.impl;

import com.MJJLB.FreshCrate.dto.CustomerDTO;
import com.MJJLB.FreshCrate.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createCustomer(CustomerDTO customer) {
        String sql = "INSERT INTO Customer (id, firstName, lastName, email, phonenumber) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhoneNumber());
    }

    @Override
    public List<CustomerDTO> getCustomer(Integer id,
                                         String firstName,
                                         String lastName,
                                         String email,
                                         String phoneNumber) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Customer WHERE 1=1 ");

        if (id != null) {
            sql.append(" AND id = ?");
            params.add(id);
        }
        if (StringUtils.hasText(firstName)) {
            sql.append(" AND firstName ILIKE ?");
            params.add("%" + firstName + "%");
        }
        if (StringUtils.hasText(lastName)) {
            sql.append(" AND lastName ILIKE ?");
            params.add("%" + lastName + "%");
        }
        if (StringUtils.hasText(email)) {
            sql.append(" AND email ILIKE ?");
            params.add("%" + email + "%");
        }
        if (StringUtils.hasText(phoneNumber)) {
            sql.append(" AND phoneNumber ILIKE ?");
            params.add("%" + phoneNumber + "%");
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new CustomerDTO(
                rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("phonenumber")
        ));

    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        String sql = "SELECT * FROM Customer";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CustomerDTO(
                rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("phonenumber")
        ));
    }

    @Override
    public void updateCustomerEmail(int id, String email) {
        String sql = "UPDATE Customer SET email = ? WHERE id = ?";
        jdbcTemplate.update(sql, email, id);
    }

    @Override
    public void updateCustomerPhoneNumber(int id, String phoneNumber) {
        String sql = "UPDATE Customer SET phoneNumber = ? WHERE id = ?";
        jdbcTemplate.update(sql, phoneNumber, id);
    }

    @Override
    public void deleteCustomerById(int id) {
        String sql = "DELETE FROM Customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
