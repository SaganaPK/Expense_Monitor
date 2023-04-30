package com.example.expensemonitor.Fragments;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.expensemonitor.Activities.showProductActivity;
import com.example.expensemonitor.Adapters.CalendarAdapter;
import com.example.expensemonitor.Model.Product;
import com.example.expensemonitor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewProductFragment extends Fragment {
        private EditText productFrag;
        private EditText quantityFrag;
        private Spinner unitSpinner;
        private EditText priceFrag;
        private EditText setdateFrag;
        private EditText storeFrag;
        private Button buttonFrag;

        FirebaseFirestore firestore;
        public void showDatePickerDialog(View v){
            setdateFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
             //   CalendarAdapter calendaradapter = new CalendarAdapter(setdateFrag);
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),  new CalendarAdapter(setdateFrag), year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
    }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.new_product_fragment, container, false);


            // Get references to the EditText views and button
            productFrag = view.findViewById(R.id.productFrag);
            quantityFrag = view.findViewById(R.id.quantityFrag);
            Spinner productTypeSpinner = view.findViewById(R.id.product_typeSpinner);
            unitSpinner = view.findViewById(R.id.unitSpinner);
            priceFrag = view.findViewById(R.id.priceFrag);
            setdateFrag = view.findViewById(R.id.setdateFrag);
            storeFrag = view.findViewById(R.id.storeFrag);
            buttonFrag = view.findViewById(R.id.buttonFrag);
            /*setdateFrag.setFocusable(false);
            setdateFrag.setClickable(true);*/

            //adapter for unit type
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.empty_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            unitSpinner.setAdapter(adapter);

            //
            productTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    if (selectedItem.equals("Liquid")) {
                        ArrayAdapter<CharSequence> liquidAdapter = ArrayAdapter.createFromResource(getContext(),
                                R.array.liquid_units, android.R.layout.simple_spinner_item);
                        liquidAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        unitSpinner.setAdapter(liquidAdapter);
                    } else if (selectedItem.equals("Weight")) {
                        ArrayAdapter<CharSequence> weightAdapter = ArrayAdapter.createFromResource(getContext(),
                                R.array.weight_units, android.R.layout.simple_spinner_item);
                        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        unitSpinner.setAdapter(weightAdapter);
                    } else if (selectedItem.equals("Quantity")) {
                        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(getContext(),
                                R.array.quantity_units, android.R.layout.simple_spinner_item);
                        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        unitSpinner.setAdapter(quantityAdapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });

            // Set an onclickListener for datepickerdialog
            setdateFrag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog(v);
                }
            });


            // Set a click listener for the Add button
            buttonFrag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addProduct();
                }
            });

            return view;
        }

        private void addProduct() {
            // Get the values from the EditText views
            String name = productFrag.getText().toString();
            name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            double quantity = Double.parseDouble(quantityFrag.getText().toString());
            String unit = unitSpinner.getSelectedItem().toString();
            String purchaseDate = setdateFrag.getText().toString();
            String storeName = storeFrag.getText().toString();
            storeName = storeName.substring(0, 1).toUpperCase() + storeName.substring(1).toLowerCase();
            double price = Double.parseDouble(priceFrag.getText().toString());


            // Create a new Product object with the values from the EditText views
            Product product = new Product(name, quantity, price, purchaseDate, storeName);

            // Save the product data to a database or API
            // For example, you might use Firebase Realtime Database to store the product data

            firestore = FirebaseFirestore.getInstance();
            CollectionReference firestoreproduct = firestore.collection("ProductList");

            // below method is use to add data to Firebase Firestore.
            firestoreproduct.add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    //Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"Failure",Toast.LENGTH_LONG).show();

                }
            });
            // Clear the EditText views
            productFrag.setText("");
            quantityFrag.setText("");
            priceFrag.setText("");
            setdateFrag.setText("");
            storeFrag.setText("");
            // Show a message to the user to indicate that the product was added
            Toast.makeText(getContext(), "Product added successfully", Toast.LENGTH_SHORT).show();

        }
}
