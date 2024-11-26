import axios from 'axios';

const API_URL = 'http://localhost:8081'; // Base URL for the backend

export const getAllCustomers = async () => {
    try {
        const response = await axios.get(`${API_URL}/getAllCustomers`);
        return response.data;
    } catch (error) {
        console.error('Error fetching customers:', error);
        throw error;
    }
};

export const createCustomer = async (customerData) => {
    try {
        const response = await axios.post(`${API_URL}/createCustomer`, customerData);
        return response.data;
    } catch (error) {
        console.error('Error creating customer:', error);
        throw error;
    }
};
