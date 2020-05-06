package com.example.sportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Record extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private ArrayList<Integer> squatVolume = new ArrayList<>(); //從firebase得到深蹲的重量
    private ArrayList<Integer> benchVolume = new ArrayList<>(); //從firebase得到握推的重量
    private ArrayList<Integer> deadliftVolume = new ArrayList<>(); //從firebase得到硬舉的重量

    private ArrayList<Integer> saveDate = new ArrayList<>(); //save date static from firebase
    //private ArrayList<Integer> weightVolume = new ArrayList<>(); //save weight static from firebase

    private EditText sport, weight, set, rep, date;
    private Button btnSave, return_btn, btnGraph, btn_search, btn_oldman;
    DatabaseReference  squat_reference, bench_reference, dead_reference;
    User user = new User();
    int which_sport = 1;

    private String name, age, injury_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        sport = findViewById(R.id.edit_sport);
        weight = findViewById(R.id.edit_weight);
        set = findViewById(R.id.edit_set);
        rep = findViewById(R.id.edit_reps);
        date = findViewById(R.id.edit_date);
        btnSave = findViewById(R.id.save);

        //reference = FirebaseDatabase.getInstance().getReference().child("TEST");
        squat_reference = FirebaseDatabase.getInstance().getReference().child("SQUAT");
        bench_reference = FirebaseDatabase.getInstance().getReference().child("BENCH");
        dead_reference = FirebaseDatabase.getInstance().getReference().child("DEADLIFT");

        //go to profile activity
        return_btn = findViewById(R.id.btn_return);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Record.this, MainActivity.class));
            }
        });

        //go to see the graph
        /*btnGraph = findViewById(R.id.btn_graph);
        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Record.this, ShowGraph.class);
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("liststr", squatVolume);
                bundle.putIntegerArrayList("date", saveDate);
                bundle.putIntegerArrayList("weight", weightVolume);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

         */

        //go to search activity
        btn_search = findViewById(R.id.go_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Record.this, Search.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameValue", name);
                bundle.putString("ageValue", age);
                bundle.putString("hurtValue", injury_history);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //go to oldman
        btn_oldman = findViewById(R.id.go_oldman);
        btn_oldman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Record.this, OldmanResult.class));
            }
        });

        //save what you just insert
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int weightt = Integer.parseInt((weight.getText().toString().trim()));
                int sett = Integer.parseInt(set.getText().toString().trim());
                int repp = Integer.parseInt(rep.getText().toString().trim());
                int datee = Integer.parseInt(date.getText().toString().trim());

                switch (sport.getText().toString()) {
                    case "squat":
                        user.setSportName(sport.getText().toString().trim());
                        user.setWeight(weightt);
                        user.setSet(sett);
                        user.setReps(repp);
                        user.setDate(datee);
                        squat_reference.push().setValue(user);
                        Toast.makeText(Record.this, "Insert Squat Success !!", Toast.LENGTH_SHORT).show();
                        break;

                    case "bench":
                        user.setSportName(sport.getText().toString().trim());
                        user.setWeight(weightt);
                        user.setSet(sett);
                        user.setReps(repp);
                        user.setDate(datee);
                        bench_reference.push().setValue(user);
                        Toast.makeText(Record.this, "Insert Bench Success !!", Toast.LENGTH_SHORT).show();
                        break;

                    case "deadlift":
                        user.setSportName(sport.getText().toString().trim());
                        user.setWeight(weightt);
                        user.setSet(sett);
                        user.setReps(repp);
                        dead_reference.push().setValue(user);
                        user.setDate(datee);
                        Toast.makeText(Record.this, "Insert Deadliftt Success !!", Toast.LENGTH_SHORT).show();
                        break;
                }


                /*
                if( sport.getText().toString() =="squat"){
                    member.setSportName(sport.getText().toString().trim());
                    member.setWeight(weightt);
                    member.setSet(sett);
                    member.setReps(repp);
                    squat_reference.push().setValue(member);
                    Toast.makeText(MainActivity.this, "Insert Squat Success !!", Toast.LENGTH_SHORT).show();
                } else {
                    member.setSportName(sport.getText().toString().trim());
                    member.setWeight(weightt);
                    member.setSet(sett);
                    member.setReps(repp);
                    bench_reference.push().setValue(member);
                    Toast.makeText(MainActivity.this, sport.getText().toString() , Toast.LENGTH_SHORT).show();
                }


                 */
                //member.setSportName(sport.getText().toString().trim());
                //member.setWeight(weightt);
                //member.setSet(sett);
                //member.setReps(repp);

                //reference.child("member").setValue(member);
                //reference.push().setValue(member);
                //Toast.makeText(MainActivity.this, "Insert Success !!", Toast.LENGTH_SHORT).show();
            }
        });

        //Get squat data
        //Query query = FirebaseDatabase.getInstance().getReference("SQUAT").orderByChild("date");
        //query.addListenerForSingleValueEvent(valueEventListener);
        Log.d("SQUAT_QUERY", squatVolume.toString());

        //Get bench press data
        //Query query２ = FirebaseDatabase.getInstance().getReference("BENCH").orderByChild("date");
        //query２.addListenerForSingleValueEvent(BenchValueEventListener);
        //Log.d("BENCH_QUERY", benchVolume.toString());

        //Get deadlift data
        //Query query3 = FirebaseDatabase.getInstance().getReference("DEADLIFT").orderByChild("date");
        //query3.addListenerForSingleValueEvent(DeadliftValueEventListener);
        Log.d("DEADLIFT_QUERY", deadliftVolume.toString());

        getVar();
    }

    //設定深蹲的listener
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    squatVolume.add(user.getWeight()); //深蹲重量
                    saveDate.add(user.getDate());
                    Log.v("Listener_squat", squatVolume.toString());
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    //設定臥推的listener
    ValueEventListener BenchValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    benchVolume.add(user.getWeight());  //臥推重量
                    saveDate.add(user.getDate());
                    Log.v("ListenerBench", benchVolume.toString());
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    //設定硬舉的listener
    ValueEventListener DeadliftValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    deadliftVolume.add(user.getWeight());  //硬舉重量
                    saveDate.add(user.getDate());
                    Log.v("ListenerDeadlift", deadliftVolume.toString());
                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    //show popup in selecting sport and can also select which sport graph to display
    public void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.sport_menu);
        popupMenu.show();
    }

    public void graphPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.graph_menu);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item1:
                Toast.makeText(Record.this, "Click Item1!" , Toast.LENGTH_SHORT).show();
                sport.setText("squat");
                return true;
            case R.id.item2:
                Toast.makeText(Record.this, menuItem.getItemId() , Toast.LENGTH_SHORT).show();
                sport.setText("bench");
                return true;
            case R.id.item3:
                Toast.makeText(Record.this, "Click Item3!" , Toast.LENGTH_SHORT).show();
                sport.setText("deadlift");
                return true;

            case R.id.item4:
                which_sport = 1;
                Query query = FirebaseDatabase.getInstance().getReference("SQUAT").orderByChild("date");
                query.addListenerForSingleValueEvent(valueEventListener);
                Log.d("SQUAT_QUERY", squatVolume.toString());
                //Log.d("which:", Integer.toString(which_sport));
                Intent intent = new Intent();
                intent.setClass(Record.this, ShowGraph.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sport", which_sport);
                bundle.putIntegerArrayList("liststr", squatVolume);
                bundle.putIntegerArrayList("date", saveDate);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;

            case R.id.item5:
                which_sport = 2;
                Query query２ = FirebaseDatabase.getInstance().getReference("BENCH").orderByChild("date");
                query２.addListenerForSingleValueEvent(BenchValueEventListener);
                Log.d("BenchQuery", benchVolume.toString());
                Intent intent2 = new Intent();
                intent2.setClass(Record.this, ShowGraph.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("sport", which_sport);
                bundle2.putIntegerArrayList("benchData", benchVolume);
                bundle2.putIntegerArrayList("date", saveDate);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                return true;

            case R.id.item6:
                which_sport = 3;
                Query query3 = FirebaseDatabase.getInstance().getReference("DEADLIFT").orderByChild("date");
                query3.addListenerForSingleValueEvent(DeadliftValueEventListener);
                Intent intent3 = new Intent();
                intent3.setClass(Record.this, ShowGraph.class);
                Bundle bundle3 = new Bundle();
                bundle3.putInt("sport", which_sport);
                bundle3.putIntegerArrayList("deadliftData", deadliftVolume);
                bundle3.putIntegerArrayList("date", saveDate);
                intent3.putExtras(bundle3);
                startActivity(intent3);
                return true;

            default:
                return false;

        }
    }

    private void getVar() {
        Bundle bundle = this.getIntent().getExtras();
        name = bundle.getString("nameValue");
        age = bundle.getString("ageValue");
        injury_history = bundle.getString("hurtValue");
    }
}
