package com.foodie.app.service;

import com.foodie.app.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MockCustomerService implements CustomerService {
    private final List<Customer> customers = new ArrayList<>(List.of(
        new Customer("CUST-1001", "Nimal Perera", "071 234 5678", 28, 25740.00, "Active", null),
        new Customer("CUST-1002", "Kavindu Silva", "076 345 6789", 15, 13850.00, "Active", null),
        new Customer("CUST-1003", "Samanthi Fernando", "077 456 7890", 42, 48920.00, "Active", null),
        new Customer("CUST-1004", "Dilshan Rodrigo", "071 567 8901", 8, 7560.00, "Active", null),
        new Customer("CUST-1005", "Tharindi Jayawardena", "075 678 9012", 22, 19430.00, "Active", null),
        new Customer("CUST-1006", "Pasindu Alwis", "078 789 0123", 5, 4250.00, "Inactive", null),
        new Customer("CUST-1007", "Dinithi Rathnayake", "072 890 1234", 31, 27680.00, "Active", null),
        new Customer("CUST-1008", "Manjula De Silva", "071 901 2345", 12, 10320.00, "Inactive", null)
    ));

    @Override
    public CompletableFuture<List<Customer>> getCustomers() {
        return CompletableFuture.completedFuture(new ArrayList<>(customers));
    }

    @Override
    public CompletableFuture<Customer> addCustomer(Customer customer) {
        customers.add(customer);
        return CompletableFuture.completedFuture(customer);
    }

    @Override
    public CompletableFuture<Customer> updateCustomerStatus(String id, String newStatus) {
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            if (c.id().equals(id)) {
                Customer updated = new Customer(c.id(), c.name(), c.phone(), c.totalOrders(), c.totalSpent(), newStatus, c.password());
                customers.set(i, updated);
                return CompletableFuture.completedFuture(updated);
            }
        }
        return CompletableFuture.failedFuture(new RuntimeException("Customer not found"));
    }
}
