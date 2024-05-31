package com.MJJLB.FreshCrate.dto;

public class CustomerAddressDTO {
    private Integer id;
    private String street;
    private String city;
    private String zipCode;
    private String stateID;
    private Integer customerId;

    public CustomerAddressDTO(Integer id, String street, String city, String zipCode, String stateID, Integer customerId) {
        setId(id);
        setStreet(street);
        setCity(city);
        setZipCode(zipCode);
        setStateID(stateID);
        setCustomerId(customerId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
