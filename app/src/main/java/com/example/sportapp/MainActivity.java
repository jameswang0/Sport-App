package com.example.sportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button old_btn, young_btn;
    private EditText nameValue, ageValue, hurtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameValue = findViewById(R.id.name);
        ageValue = findViewById(R.id.age);
        hurtValue = findViewById(R.id.hurt_history);

        //For Old Man
        old_btn = findViewById(R.id.button1);
        old_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, OldmanResult.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nameValue", nameValue.getText().toString());
                    bundle.putString("ageValue", ageValue.getText().toString());
                    bundle.putString("hurtValue", hurtValue.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Please Enter Completely ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //For Young Man
        young_btn = findViewById(R.id.button2);
        young_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Search.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nameValue", nameValue.getText().toString());
                    bundle.putString("ageValue", ageValue.getText().toString());
                    bundle.putString("hurtValue", hurtValue.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Please Enter Completely ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
