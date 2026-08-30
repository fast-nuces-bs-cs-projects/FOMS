package com.foodie.app.service;

import com.foodie.app.model.Customer;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CustomerService {
    CompletableFuture<List<Customer>> getCustomers();
    CompletableFuture<Customer> addCustomer(Customer customer);
    CompletableFuture<Customer> updateCustomerStatus(String id, String newStatus);
}
