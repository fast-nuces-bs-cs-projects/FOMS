package com.foodie.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodie.app.R;
import com.foodie.app.models.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> items = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAddToCartClick(FoodItem item);
    }

    public FoodAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<FoodItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem item = items.get(position);
        
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            String baseUrl = "http://10.0.2.2:8081";
            String fullImageUrl = baseUrl + item.getImageUrl();
            com.bumptech.glide.Glide.with(holder.itemView.getContext())
                .load(fullImageUrl)
                .placeholder(R.drawable.ic_food_burger) // Fallback placeholder
                .into(holder.imageView);
        } else if (item.getImageResId() != 0) {
            holder.imageView.setImageResource(item.getImageResId());
        } else {
            holder.imageView.setImageResource(R.drawable.ic_food_burger);
        }

        holder.titleView.setText(item.getTitle());
        holder.descView.setText(item.getDesc());
        holder.priceView.setText(item.getPrice());

        holder.addToCartBtn.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToCartClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        android.widget.ImageView imageView;
        TextView titleView;
        TextView descView;
        TextView priceView;
        View addToCartBtn;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.food_image);
            titleView = itemView.findViewById(R.id.food_title);
            descView = itemView.findViewById(R.id.food_desc);
            priceView = itemView.findViewById(R.id.food_price);
            addToCartBtn = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}
