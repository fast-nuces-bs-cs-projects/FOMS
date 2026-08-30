package com.foodie.app.service;

import com.foodie.app.model.DashboardData.OrderStatus;
import com.foodie.app.model.OrdersData;
import com.foodie.app.model.OrdersData.OrderRow;
import com.foodie.app.model.OrdersData.OrderType;

import java.math.BigDecimal;
import java.util.List;

public final class MockOrdersDataProvider implements OrdersDataProvider {
    @Override
    public OrdersData loadOrders() {
        OrderRow incoming = order("#ORD-1249", true, "Ali Raza", "0312-5551234",
                "Zinger Burger, Fries", "Coke (500ml)", OrderType.DELIVERY,
                "Cash on Delivery", "12.50", OrderStatus.PENDING, "Just now");
        return new OrdersData(1_248, 32, 18, 26, incoming, List.of(
                order("#ORD-1248", false, "John Doe", "0300-1112233", "Classic Cheeseburger", "Fries, Coke (500ml)", OrderType.DELIVERY, "EasyPaisa", "11.99", OrderStatus.PENDING, "1 min ago"),
                order("#ORD-1247", false, "Sarah Johnson", "0333-4445566", "Margherita Pizza", "", OrderType.PICKUP, "Cash on Delivery", "8.50", OrderStatus.PREPARING, "4 min ago"),
                order("#ORD-1246", false, "Michael Brown", "0321-7778899", "Spicy Chicken Burger", "Fries, Sprite (500ml)", OrderType.DELIVERY, "EasyPaisa", "13.49", OrderStatus.READY, "7 min ago"),
                order("#ORD-1245", false, "Emily Davis", "0311-2223344", "Pepperoni Pizza", "Garlic Bread", OrderType.DELIVERY, "Credit/Debit Card", "15.99", OrderStatus.COMPLETED, "15 min ago"),
                order("#ORD-1244", false, "David Wilson", "0345-6667788", "Chicken Wrap", "Fries, Ice Tea", OrderType.PICKUP, "Cash on Delivery", "9.75", OrderStatus.COMPLETED, "22 min ago"),
                order("#ORD-1243", false, "Ayesha Khan", "0309-8889900", "Chocolate Lava Cake", "Coke (500ml)", OrderType.DELIVERY, "EasyPaisa", "6.50", OrderStatus.COMPLETED, "28 min ago"),
                order("#ORD-1242", false, "James Smith", "0330-4447788", "Double Cheeseburger", "Fries, Pepsi (500ml)", OrderType.DELIVERY, "Cash on Delivery", "14.25", OrderStatus.COMPLETED, "35 min ago")
        ));
    }

    private OrderRow order(String id, boolean isNew, String customer, String phone, String items,
                           String extras, OrderType type, String payment, String amount,
                           OrderStatus status, String time) {
        return new OrderRow(id, isNew, customer, phone, items, extras, type, payment,
                new BigDecimal(amount), status, time);
    }
}
