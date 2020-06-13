package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import static com.example.myapplication.R.id.addStudentId;
import static com.example.myapplication.R.id.takeAttendenceId;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button addStudentBtn,takeAttendenceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addStudentBtn = findViewById(addStudentId);
        takeAttendenceBtn = findViewById(takeAttendenceId);

        addStudentBtn.setOnClickListener(this);
        takeAttendenceBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId()== addStudentId){

            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);
        }

        if (view.getId()== takeAttendenceId){

            Intent intent = new Intent(MainActivity.this,ThirdActivity.class);
            startActivity(intent);
        }

    }
}
