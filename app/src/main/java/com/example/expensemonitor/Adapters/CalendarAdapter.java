package com.example.expensemonitor.Adapters;

import android.content.Context;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensemonitor.R;

import java.util.Calendar;

public class CalendarAdapter  implements DatePickerDialog.OnDateSetListener {

    private EditText editText;
    public CalendarAdapter(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
        editText.setText(selectedDate);
    }
}
