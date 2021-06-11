package com.example.mystoreapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mystoreapp.data.ProductItem;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    ArrayList<ProductItem> listItems = new ArrayList<>();

    private final ProductClickListener productClickListener;

    private final Context context;

    public ProductAdapter(Context context, ProductClickListener listener) {
        this.productClickListener = listener;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(v -> {
            int index = viewHolder.getAdapterPosition();
            productClickListener.onProductClick(listItems.get(index));
        });
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        ProductItem current = listItems.get(position);

        holder.getProductTV().setText(current.getProductName().substring(0,1).toUpperCase() + current.getProductName().substring(1));
        holder.getBrandTV().setText(current.getBrandName().toUpperCase());
        holder.getInStockTV().setText(current.isInStock() ? "AVAILABLE" : "NOT AVAILABLE");
        holder.getPriceTV().setText("₹ " + current.getPrice());
        holder.getWeightTV().setText(current.getWeight() + " g");

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public ProductItem getItemAt(int idx){
        return listItems.get(idx);
    }

    public void updateItems(List<ProductItem> newList) {
        listItems.clear();
        listItems.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView productTV, brandTV, inStockTV, weightTV, priceTV;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            productTV = itemView.findViewById(R.id.productName);
            brandTV = itemView.findViewById(R.id.brandName);
            inStockTV = itemView.findViewById(R.id.inStockcard);
            weightTV = itemView.findViewById(R.id.weight);
            priceTV = itemView.findViewById(R.id.pricecard);
        }

        public TextView getProductTV() {
            return productTV;
        }

        public TextView getBrandTV() {
            return brandTV;
        }

        public TextView getInStockTV() {
            return inStockTV;
        }

        public TextView getWeightTV() {
            return weightTV;
        }

        public TextView getPriceTV() {
            return priceTV;
        }
    }


}

interface ProductClickListener {
    void onProductClick(ProductItem item);
}