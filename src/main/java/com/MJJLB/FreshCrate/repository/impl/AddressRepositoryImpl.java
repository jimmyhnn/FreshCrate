package com.MJJLB.FreshCrate.repository.impl;

import com.MJJLB.FreshCrate.dto.CustomerAddressDTO;
import com.MJJLB.FreshCrate.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AddressRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createAddress(CustomerAddressDTO address) {
        String sql = "INSERT INTO Address (id, customerId, street, city, zipCode, stateID) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, address.getId(), address.getCustomerId(), address.getStreet(), address.getCity(),
                address.getZipCode(), address.getStateID());
    }

    @Override
    public void updateAddress(Integer customerId, String street, String city, String zipCode, String stateId) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("UPDATE Address SET ");

        boolean first = true;

        if (StringUtils.hasText(street)) {
            sql.append("street = ?");
            params.add(street);
            first = false;
        }
        if (StringUtils.hasText(city)) {
            if (!first)
                sql.append(", ");
            sql.append("city = ?");
            params.add(city);
            first = false;
        }
        if (StringUtils.hasText(zipCode)) {
            if (!first)
                sql.append(", ");
            sql.append("zipCode = ?");
            params.add(zipCode);
            first = false;
        }
        if (stateId != null) {
            if (!first)
                sql.append(", ");
            sql.append("stateID = ?");
            params.add(stateId);
        }

        sql.append(" WHERE customerId = ?");
        params.add(customerId);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void deleteAddress(Integer customerId) {
        String sql = "DELETE FROM Address WHERE customerId = ?";
        jdbcTemplate.update(sql, customerId);
    }

    @Override
    public List<CustomerAddressDTO> getAddressByCustomerId(Integer customerId) {
        String sql = "SELECT id, customerId, street, city, zipCode, stateId FROM Address WHERE customerId = ?";
        return jdbcTemplate.query(
                sql,
                ps -> ps.setObject(1, customerId), // Set the first parameter (customerId)
                (rs, rowNum) -> new CustomerAddressDTO(
                        rs.getInt("id"),
                        rs.getString("street"),
                        rs.getString("city"),
                        rs.getString("zipCode"),
                        rs.getString("stateID"),
                        rs.getInt("customerId")));

    }

    @Override
    public List<CustomerAddressDTO> getAllAddresses() {
        String sql = "SELECT * From Address";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CustomerAddressDTO(
                rs.getInt("id"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("zipCode"),
                rs.getString("stateID"),
                rs.getInt("customerId")));
    }
}
