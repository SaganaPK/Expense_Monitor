package com.example.expensemonitor.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemonitor.Fragments.showPriceTrendFragment;
import com.example.expensemonitor.Model.Product;
import com.example.expensemonitor.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class showProductAdapter extends RecyclerView.Adapter<showProductAdapter.ViewHolder> {

    private OnItemClickListener uprodListener;
    private ArrayList<String> productList;

    public interface OnItemClickListener {
        void onItemClick(String productName);
    }

    public void setOnItemClickListener(OnItemClickListener uprodListener) {
        this.uprodListener = uprodListener;
    }
    public showProductAdapter(ArrayList<String> productList,OnItemClickListener uprodListener) {
        this.productList = productList;
        this.uprodListener = uprodListener;
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
    public void onBindViewHolder(ViewHolder holder,int position) {
        String productName = productList.get(position);
        holder.productName.setText(productName);
        // Set an onClickListener for the product name text view
        holder.productName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the onItemClick method of the listener with the selected product name
                if (uprodListener != null) {
                    uprodListener.onItemClick(productName);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

}
