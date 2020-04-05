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

    private ArrayList<Integer> squatVolume = new ArrayList<>();
    private ArrayList<Integer> saveDate = new ArrayList<>();

    private EditText sport, weight, set, rep, date;
    private Button btnSave, return_btn, btnGraph;
    DatabaseReference reference, squat_reference, bench_reference, dead_reference;
    User user = new User();

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

        reference = FirebaseDatabase.getInstance().getReference().child("TEST");
        squat_reference = FirebaseDatabase.getInstance().getReference().child("SQUAT");
        bench_reference = FirebaseDatabase.getInstance().getReference().child("BENCH");
        dead_reference = FirebaseDatabase.getInstance().getReference().child("DEADLIFT");

        return_btn = findViewById(R.id.btn_return);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Record.this, MainActivity.class));
            }
        });

        btnGraph = findViewById(R.id.btn_graph);
        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Record.this, ShowGraph.class);
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("liststr", squatVolume);
                bundle.putIntegerArrayList("date", saveDate);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float weightt = Float.parseFloat((weight.getText().toString().trim()));
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

        Query query = FirebaseDatabase.getInstance().getReference("SQUAT")
                .orderByChild("date");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    squatVolume.add(user.getReps());
                    saveDate.add(user.getDate());
                    Log.v("MainAcyivity", squatVolume.toString());

                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.sport_menu);
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
            default:
                return false;

        }
    }
}
