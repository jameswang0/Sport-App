package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ShowGraph extends AppCompatActivity {

    private ArrayList<Integer> squatVolume = new ArrayList<>();
    private ArrayList<Integer> weightVolume = new ArrayList<>();
    private String name, age, injury_history;

    private LineChart mChart;
    private LineData data1;

    private Button returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_graph);

        returnBtn = findViewById(R.id.button6);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowGraph.this, MainActivity.class));
            }
        });

        mChart = findViewById(R.id.lineChart);
        initChart(mChart); //初始化畫圖的基本設定

        getVar(); //get data from bundle
        getValue(); //獲取畫圖所需的數據
        Log.v("ShowGraph", squatVolume.toString());
        setChart(); //畫圖
    }

    //畫圖的基本設定
    private void initChart(LineChart lineChart) {

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
    }

    private void getValue() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        squatVolume = bundle.getIntegerArrayList("liststr"); //get squat static you insert in Record.java and from firebase

        weightVolume = bundle.getIntegerArrayList("weight"); //get weight static you insert in Record.java and from firebase
    }

    //畫圖
    private void setChart() {

        ArrayList<Entry> yValues = new ArrayList<>();

        /*
        yValues.add(new Entry(0, 60f));
        yValues.add(new Entry(1, 50f));
        yValues.add(new Entry(2, 70f));
        yValues.add(new Entry(3, 30f));
        yValues.add(new Entry(4, 50f));
        yValues.add(new Entry(5, 60f));
        yValues.add(new Entry(6, 20f));

         */
        for(int i=0;i<squatVolume.size();i++) {
            //yValues.add(new Entry(i, squatVolume.get(i)));
            yValues.add(new Entry(i, weightVolume.get(i)));

        }

        LineDataSet set1 = new LineDataSet(yValues, "Weight");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        data1 = new LineData(dataSets);

        mChart.setData(data1);
    }

    private void getVar() {
        Bundle bundle = this.getIntent().getExtras();
        name = bundle.getString("nameValue");
        age = bundle.getString("ageValue");
        injury_history = bundle.getString("hurtValue");
    }
}
