package com.example.expensemonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.expensemonitor.Activities.NewProductActivity;
import com.example.expensemonitor.Activities.showProductActivity;
import com.example.expensemonitor.Adapters.CalendarAdapter;
import com.example.expensemonitor.Fragments.NewProductFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText setdateET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("------------------------------------------CHECK----------------------------");



        Button addProductButton = findViewById(R.id.addBTN);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
                startActivity(intent);
            }
        });
       Button showProductButton = findViewById(R.id.showBTN);
        showProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, showProductActivity.class);
                startActivity(intent);
            }
        });
    }
}