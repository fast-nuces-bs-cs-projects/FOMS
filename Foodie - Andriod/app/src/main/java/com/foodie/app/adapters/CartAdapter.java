package com.foodie.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodie.app.R;
import com.foodie.app.models.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> items = new ArrayList<>();
    private final OnCartItemClickListener listener;

    public interface OnCartItemClickListener {
        void onIncrease(CartItem item);
        void onDecrease(CartItem item);
        void onDelete(CartItem item);
    }

    public CartAdapter(OnCartItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = items.get(position);
        
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            String baseUrl = "http://10.0.2.2:8081";
            String fullImageUrl = baseUrl + item.getImageUrl();
            com.bumptech.glide.Glide.with(holder.itemView.getContext())
                .load(fullImageUrl)
                .placeholder(R.drawable.ic_food_burger)
                .into(holder.imageView);
        } else if (item.getImageResId() != 0) {
            holder.imageView.setImageResource(item.getImageResId());
        } else {
            holder.imageView.setImageResource(R.drawable.ic_food_burger);
        }

        holder.titleView.setText(item.getTitle());
        holder.descView.setText(item.getDesc());
        holder.priceView.setText(item.getPrice());
        holder.quantityView.setText(String.valueOf(item.getQuantity()));

        holder.btnPlus.setOnClickListener(v -> {
            if (listener != null) listener.onIncrease(item);
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (listener != null) listener.onDecrease(item);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        android.widget.ImageView imageView;
        TextView titleView;
        TextView descView;
        TextView priceView;
        TextView quantityView;
        View btnPlus;
        View btnMinus;
        View btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cart_food_image);
            titleView = itemView.findViewById(R.id.cart_food_title);
            descView = itemView.findViewById(R.id.cart_food_desc);
            priceView = itemView.findViewById(R.id.cart_food_price);
            quantityView = itemView.findViewById(R.id.tv_quantity);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
