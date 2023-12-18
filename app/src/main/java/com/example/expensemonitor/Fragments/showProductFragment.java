package com.example.expensemonitor.Fragments;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemonitor.Adapters.showProductAdapter;
import com.example.expensemonitor.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class showProductFragment extends Fragment implements showProductAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private showProductAdapter adapter;
    FirebaseFirestore firestore;
    private ArrayList<String> productList = new ArrayList<>();

    public showProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_product_fragment, container, false);

        recyclerView = view.findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        firestore = FirebaseFirestore.getInstance();

        firestore.collection("ProductList").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String productName = document.getData().get("name").toString();
                    if (!productList.contains(productName)) {
                        productList.add(productName);
                    }
                }
                adapter = new showProductAdapter(productList, this);
                recyclerView.setAdapter(adapter);
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
        return view;
    }

    @Override
    public void onItemClick(String productName) {
        // Create a new instance of the price trend fragment
        showPriceTrendFragment showpricetrendfragment = new showPriceTrendFragment();

        // Create a bundle to pass the selected product name as an argument
        Bundle args = new Bundle();
        args.putString("productName", productName);
        showpricetrendfragment.setArguments(args);

        // Launch the price trend fragment
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, showpricetrendfragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
