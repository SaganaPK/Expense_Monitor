package com.example.expensemonitor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemonitor.Model.Product;
import com.example.expensemonitor.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class showProductAdapter extends RecyclerView.Adapter<showProductAdapter.ViewHolder> {

    private ArrayList<String> productList;
    public showProductAdapter(ArrayList<String> productList) {
        this.productList = productList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.showProductName);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_product_rvlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }
       /* public showProductAdapter(@NonNull FirestoreRecyclerOptions<String> options) {
            super(options);
        }


    @Override
        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull String name) {
            holder.productName.setText(name);
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_product_rvlayout, parent, false);
            return new ProductViewHolder(view);
        }

        public static class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView productName;

            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                productName = itemView.findViewById(R.id.showProductName);
            }
        }*/

}