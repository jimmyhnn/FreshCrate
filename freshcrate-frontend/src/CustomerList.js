import React, { useEffect, useState } from 'react';
import { getAllCustomers } from './api';

const CustomerList = () => {
    const [customers, setCustomers] = useState([]);

    useEffect(() => {
        // Fetch all customers when the component loads
        const fetchCustomers = async () => {
            try {
                const data = await getAllCustomers();
                setCustomers(data);
            } catch (error) {
                console.error('Failed to fetch customers:', error);
            }
        };

        fetchCustomers();
    }, []);

    return (
        <div>
            <h1>Customer List</h1>
            <ul>
                {customers.map((customer) => (
                    <li key={customer.id}>
                        {customer.firstName} {customer.lastName} - {customer.email} - {customer.phoneNumber}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CustomerList;
