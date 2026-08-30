package com.foodie.app.service;

import com.foodie.app.api.ApiClient;
import com.foodie.app.model.Customer;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class HttpCustomerService implements CustomerService {
    @Override
    public CompletableFuture<List<Customer>> getCustomers() {
        return ApiClient.get("/api/customers", Customer[].class)
                .thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<Customer> addCustomer(Customer customer) {
        return ApiClient.post("/api/customers", customer, Customer.class);
    }

    @Override
    public CompletableFuture<Customer> updateCustomerStatus(String id, String newStatus) {
        // Assuming API expects a status patch or a full PUT. We'll send a tiny payload for now.
        // E.g., class StatusUpdate { String status = newStatus; }
        record StatusUpdate(String status) {}
        return ApiClient.put("/api/customers/" + id + "/status", new StatusUpdate(newStatus), Customer.class);
    }
}

