package com.example.expensemonitor.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemonitor.Fragments.NewProductFragment;
import com.example.expensemonitor.R;

public class NewProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_product_activity);
        if (!isFinishing()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new NewProductFragment())
                    .commit();
        }
    }
}
