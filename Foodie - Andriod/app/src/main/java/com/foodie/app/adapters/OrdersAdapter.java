package com.foodie.app.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodie.app.R;
import com.foodie.app.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<Order> orders = new ArrayList<>();

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        
        // title might be null if server doesn't send it, fallback to order ID
        String title = order.getTitle() != null ? order.getTitle() : "Order #" + order.getId();
        holder.titleView.setText(title);
        
        holder.itemsView.setText(order.getItems());
        holder.timeView.setText(order.getTime());
        holder.priceView.setText("Rs. " + order.getPrice());
        
        String status = order.getStatus() != null ? order.getStatus().toUpperCase() : "UNKNOWN";
        holder.statusView.setText(status);
        
        // Dynamically assign color and background based on status instead of relying on local fields
        int textColor = Color.BLACK;
        int bgResId = 0;
        
        switch (status) {
            case "PENDING":
                textColor = Color.parseColor("#E65100"); // Orange
                bgResId = R.drawable.bg_status_pending;
                break;
            case "ACCEPTED":
                textColor = Color.parseColor("#1565C0"); // Blue
                bgResId = R.drawable.bg_status_accepted;
                break;
            case "COMPLETED":
            case "DELIVERED":
                textColor = Color.parseColor("#2E7D32"); // Green
                bgResId = R.drawable.bg_status_completed;
                break;
            default:
                textColor = Color.parseColor("#888888"); // Gray
                break;
        }
        
        holder.statusView.setTextColor(textColor);
        if (bgResId != 0) {
            holder.statusView.setBackgroundResource(bgResId);
        } else {
            holder.statusView.setBackground(null);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView itemsView;
        TextView timeView;
        TextView priceView;
        TextView statusView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.order_title);
            itemsView = itemView.findViewById(R.id.order_items);
            timeView = itemView.findViewById(R.id.order_time);
            priceView = itemView.findViewById(R.id.order_price);
            statusView = itemView.findViewById(R.id.order_status);
        }
    }
}
