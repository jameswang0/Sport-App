package com.example.sportapp;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ShowGraph extends AppCompatActivity {

    private ArrayList<Integer> squatVolume = new ArrayList<>();
    private ArrayList<Integer> benchVolume = new ArrayList<>(); //從firebase得到握推的重量
    private ArrayList<Integer> weightVolume = new ArrayList<>();
    private ArrayList<Integer> saveDate = new ArrayList<>(); //save date static from firebase

    private ArrayList<Integer> date = new ArrayList<>();
    private String name, age, injury_history;
    DatabaseReference squat_reference;

    private LineChart mChart;
    private LineData data1;
    private Button returnBtn;

    int which_sport;

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

        squat_reference = FirebaseDatabase.getInstance().getReference().child("SQUAT");
        mChart = findViewById(R.id.lineChart);
        initChart(mChart); //初始化畫圖的基本設定


        getVar(); //get data from bundle
        getValue(); //獲取畫圖所需的數據
        //Log.v("ShowGraph", squatVolume.toString());



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
        date = bundle.getIntegerArrayList("date");
        which_sport = bundle.getInt("sport");

        switch (which_sport) {
            case 1:
                weightVolume = bundle.getIntegerArrayList("liststr");
                break;
            case 2:
                weightVolume = bundle.getIntegerArrayList("benchData");
                Log.d("Get bench data", weightVolume.toString());
                break;
            case 3:
                weightVolume = bundle.getIntegerArrayList("deadliftData");
                Log.d("showgrapg_deadlift", weightVolume.toString());
                break;
        }
        //weightVolume = bundle.getIntegerArrayList("deadliftData");
        //Log.d("showgrapg_deadlift", weightVolume.toString());
        //squatVolume = bundle.getIntegerArrayList("liststr"); //get squat static you insert in Record.java and from firebase
        //weightVolume = bundle.getIntegerArrayList("weight"); //get weight static you insert in Record.java and from firebase
    }

    //設定臥推的listener
    ValueEventListener BenchValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    benchVolume.add(user.getWeight());  //臥推重量
                    saveDate.add(user.getDate());
                    Log.v("Bench", benchVolume.toString());
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

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
        for(int i=0;i<date.size();i++) {
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
