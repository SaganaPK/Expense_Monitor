package com.example.expensemonitor.Fragments;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemonitor.Adapters.showProductAdapter;
import com.example.expensemonitor.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class showProductFragment extends Fragment {
    private RecyclerView recyclerView;
    private showProductAdapter adapter;
    FirebaseFirestore firestore;
    private ArrayList<String> productList;

    public showProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Initialize Firestore and the products collection reference
     //   db = FirebaseFirestore.getInstance();

        // Initialize the list of product names and the RecyclerView
       // productList = new ArrayList<>();
        /*productRecyclerView = view.findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productRecyclerView.setAdapter(adapter);*/

        View view = inflater.inflate(R.layout.show_product_fragment, container, false);

        recyclerView = view.findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        firestore = FirebaseFirestore.getInstance();
        ArrayList<String> productList = new ArrayList<>();

        firestore.collection("ProductList").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String productName = document.getData().get("name").toString();
                    if (!productList.contains(productName)) {
                        productList.add(productName);
                    }
                }
                adapter = new showProductAdapter(productList);
                recyclerView.setAdapter(adapter);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
        return view;
    }

 }