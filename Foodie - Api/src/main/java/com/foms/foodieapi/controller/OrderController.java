package com.foms.foodieapi.controller;

import com.foms.foodieapi.model.FoodItem;
import com.foms.foodieapi.model.Order;
import com.foms.foodieapi.model.OrderItem;
import com.foms.foodieapi.model.User;
import com.foms.foodieapi.repository.FoodItemRepository;
import com.foms.foodieapi.repository.OrderRepository;
import com.foms.foodieapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    // ----- FXML Dashboard APIs -----

    @GetMapping("/orders")
    public List<Map<String, Object>> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapOrderToRow)
                .collect(Collectors.toList());
    }

    @GetMapping("/orders/incoming")
    public List<Map<String, Object>> getIncomingOrders() {
        return orderRepository.findAll().stream()
                .filter(o -> "PENDING".equalsIgnoreCase(o.getStatus()))
                .map(this::mapOrderToRow)
                .collect(Collectors.toList());
    }

    private Map<String, Object> mapOrderToRow(Order order) {
        String itemsSummary = order.getItems().stream()
                .map(item -> item.getQuantity() + "x " + item.getMenuItem().getName())
                .collect(Collectors.joining(", "));
                
        System.out.println("Items Summary: '" + itemsSummary + "'");
        
        String phone = order.getUser() != null ? order.getUser().getPhone() : "";
        String customerName = order.getUser() != null ? order.getUser().getName() : "Unknown";
        String address = order.getDeliveryAddress();
        String payment = order.getPaymentMethod();
        
        String status = order.getStatus() != null ? order.getStatus() : "PENDING";
        if ("ACCEPTED".equalsIgnoreCase(status)) {
            status = "PREPARING";
        } else if ("REJECTED".equalsIgnoreCase(status)) {
            status = "CANCELLED";
        }
        
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("id", "ORD-" + order.getId());
        map.put("newOrder", false);
        map.put("customer", customerName);
        map.put("phone", phone != null ? phone : "");
        map.put("items", itemsSummary);
        map.put("extras", "");
        map.put("type", (address != null && !address.trim().isEmpty()) ? "DELIVERY" : "PICKUP");
        map.put("payment", payment != null ? payment : "");
        map.put("amount", order.getTotalAmount() != null ? order.getTotalAmount() : 0.0);
        map.put("status", status);
        map.put("time", order.getOrderDate() != null ? order.getOrderDate().toLocalTime().withNano(0).toString() : "");
        return map;
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @RequestBody Map<String, String> payload) {
        try {
            Long parsedId = Long.parseLong(id.replace("ORD-", ""));
            return orderRepository.findById(parsedId).map(order -> {
                order.setStatus(payload.get("status"));
                orderRepository.save(order);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid Order ID format");
        }
    }

    // ----- Android App APIs -----

    @GetMapping("/api/orders")
    public List<Map<String, Object>> getUserOrders(Authentication authentication) {
        String email = authentication.getName();
        return orderRepository.findAll().stream()
                .filter(order -> order.getUser().getEmail().equals(email))
                .map(order -> {
                    String itemsSummary = order.getItems().stream()
                            .map(item -> item.getMenuItem().getName())
                            .collect(Collectors.joining(", "));
                    return Map.<String, Object>of(
                            "order_id", order.getId(),
                            "items_summary", itemsSummary,
                            "date_time", order.getOrderDate().toString(),
                            "total_amount", order.getTotalAmount(),
                            "status", order.getStatus()
                    );
                }).collect(Collectors.toList());
    }

    @PostMapping("/api/orders/place")
    public ResponseEntity<?> placeOrder(Authentication authentication, @RequestBody PlaceOrderRequest request) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setDeliveryAddress(request.getDelivery_address());
        order.setPaymentMethod(request.getPayment_method());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;

        for (CartItemRequest itemReq : request.getCart_items()) {
            FoodItem foodItem = foodItemRepository.findById(itemReq.getFood_item_id())
                    .orElseThrow(() -> new RuntimeException("Food item not found"));
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(foodItem);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setUnitPrice(foodItem.getPrice());
            
            total += foodItem.getPrice() * itemReq.getQuantity();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);
        orderRepository.save(order);

        return ResponseEntity.ok(Map.of("message", "Order placed successfully", "order_id", order.getId()));
    }

    // --- DTOs ---

    public static class PlaceOrderRequest {
        private List<CartItemRequest> cart_items;
        private String delivery_address;
        private String payment_method;

        public List<CartItemRequest> getCart_items() { return cart_items; }
        public void setCart_items(List<CartItemRequest> cart_items) { this.cart_items = cart_items; }
        public String getDelivery_address() { return delivery_address; }
        public void setDelivery_address(String delivery_address) { this.delivery_address = delivery_address; }
        public String getPayment_method() { return payment_method; }
        public void setPayment_method(String payment_method) { this.payment_method = payment_method; }
    }

    public static class CartItemRequest {
        private Long food_item_id;
        private Integer quantity;

        public Long getFood_item_id() { return food_item_id; }
        public void setFood_item_id(Long food_item_id) { this.food_item_id = food_item_id; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}
