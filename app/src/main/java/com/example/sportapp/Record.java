package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Record extends AppCompatActivity {

    private EditText sport, weight, set, rep;
    private Button btnSave, return_btn;
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


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float weightt = Float.parseFloat((weight.getText().toString().trim()));
                int sett = Integer.parseInt(set.getText().toString().trim());
                int repp = Integer.parseInt(rep.getText().toString().trim());

                switch (sport.getText().toString()) {
                    case "squat":
                        user.setSportName(sport.getText().toString().trim());
                        user.setWeight(weightt);
                        user.setSet(sett);
                        user.setReps(repp);
                        squat_reference.push().setValue(user);
                        Toast.makeText(Record.this, "Insert Squat Success !!", Toast.LENGTH_SHORT).show();
                        break;

                    case "bench":
                        user.setSportName(sport.getText().toString().trim());
                        user.setWeight(weightt);
                        user.setSet(sett);
                        user.setReps(repp);
                        bench_reference.push().setValue(user);
                        Toast.makeText(Record.this, "Insert Bench Success !!", Toast.LENGTH_SHORT).show();
                        break;

                    case "deadlift":
                        user.setSportName(sport.getText().toString().trim());
                        user.setWeight(weightt);
                        user.setSet(sett);
                        user.setReps(repp);
                        dead_reference.push().setValue(user);
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
    }
}
