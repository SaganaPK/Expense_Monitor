package com.example.expensemonitor.Chart;

import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.List;

public class ChartData {
    private List<ILineDataSet> dataSets;
    private List<String> labels;

    public ChartData(List<ILineDataSet> dataSets, List<String> labels) {
        this.dataSets = dataSets;
        this.labels = labels;
    }

    public List<ILineDataSet> getDataSets() {
        return dataSets;
    }

    public List<String> getLabels() {
        return labels;
    }
}