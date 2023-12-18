package com.example.expensemonitor.Fragments;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensemonitor.Chart.ChartData;
import com.example.expensemonitor.Model.Product;
import com.example.expensemonitor.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class showPriceTrendFragment extends Fragment {
    private LineChart lineChart;
    private String productName;
    private List<Entry> entries = new ArrayList<>();
    private List<String> labels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_price_trend_fragment, container, false);
        // Get the selected product name from the arguments
        Bundle args = getArguments();
        if (args != null) {
            productName = args.getString("productName");
        }
        // Initialize the chart
        lineChart = view.findViewById(R.id.line_chart);

        // Query the database for the price trend data for the selected product
        queryDatabase(productName, new QueryCallback() {
            @Override
            public void onDataLoaded(List<Product> priceTrendDataList) {
                ChartData chartData = createDataSet(priceTrendDataList);

             //   customizeChart(lineChart, productName, dataSetResult.first, dataSetResult.second);
                Log.d(TAG, "onDataLoaded: Data is loaded - "+entries);
                customizeChart(lineChart, productName, chartData);
            }
        });
        return view;
    }

    public interface QueryCallback {
        void onDataLoaded(List<Product> priceTrendDataList);
    }

    // Method to query the database for price trend data for the selected product
    private List<Product> queryDatabase(String productName, QueryCallback callback) {
        List<Product> priceTrendDataList = new ArrayList<>();

        // Query the database for the price trend data for the selected product
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsRef = db.collection("ProductList");
        productsRef.whereEqualTo("name", productName)
                .orderBy("purchaseDate")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Product product = document.toObject(Product.class);
                            priceTrendDataList.add(product);

                        }
                        for(int i = 0;i < priceTrendDataList.size();i++){
                            Log.d(TAG, "queryDatabase : priceTrendDataList :"+priceTrendDataList.get(i));
                        }
                        callback.onDataLoaded(priceTrendDataList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error getting documents: " + e.getMessage());
                    }
                });
        return priceTrendDataList;
    }
    // Method to create a data set for the chart from the price trend data
    private ChartData createDataSet(List<Product> priceTrendDataList) {
        List<String> labels = new ArrayList<>();
        List<ILineDataSet> dataSets = new ArrayList<>();

        // Get a set of unique store names
        Set<String> storeNames = new HashSet<>();
        for (Product priceTrendData : priceTrendDataList) {
            storeNames.add(priceTrendData.getStoreName());
        }

        // Loop through each unique store name
        for (String storeName : storeNames) {
            List<Entry> entries = new ArrayList<>();

            // Loop through each price trend data and add entries for the current store name
            for (int i = 0; i < priceTrendDataList.size(); i++) {
                Product priceTrendData = priceTrendDataList.get(i);
                if (priceTrendData.getStoreName().equals(storeName)) {
                    entries.add(new Entry(i, (float) priceTrendData.getPrice()));
                    if (labels.size() <= i) {
                        labels.add(priceTrendData.getPurchaseDate());
                    }
                }
            }

            LineDataSet dataSet = new LineDataSet(entries, storeName);
            int color = getRandomColor();
            dataSet.setCircleColor(color);
            dataSet.setColor(color);
            dataSets.add(dataSet);
        }

        return new ChartData(dataSets, labels);
    }

    // Method to customize the appearance of the chart
    private void customizeChart(LineChart lineChart, String title, ChartData chartData) {
        Log.d(TAG, "customizeChart: Title: " + title);
        Log.d(TAG, "customizeChart: Labels: " + chartData.getLabels());

        lineChart.getDescription().setEnabled(true);
        lineChart.getDescription().setText(title);

        // Set X-axis properties
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(chartData.getLabels()));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setTextColor(Color.WHITE);

        // Set Y-axis properties
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setGranularity(1f);
        yAxis.setGranularityEnabled(true);
        yAxis.setTextColor(Color.WHITE);

        // Customize legend
        Legend legend = lineChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);
        legend.setTextColor(Color.WHITE);


        // Set data
        LineData lineData = new LineData(chartData.getDataSets());
        lineChart.setData(lineData);


        // Refresh chart
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawBorders(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(false);
        lineChart.setHighlightPerTapEnabled(true);
        lineChart.setHighlightPerDragEnabled(true);
        lineChart.setExtraLeftOffset(30);
        lineChart.setExtraRightOffset(30);
        lineChart.animateX(1000);
        lineChart.invalidate();
    }

    private int getRandomColor() {
        Random rand = new Random();
        return Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }
    private String[] getDateLabels() {
        String[] dateLabels = new String[entries.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.US);

        for (int i = 0; i < entries.size(); i++) {
            long dateInMillis = (long) entries.get(i).getX();
            Date date = new Date(dateInMillis);
            dateLabels[i] = sdf.format(date);
        }

        return dateLabels;
    }
}
