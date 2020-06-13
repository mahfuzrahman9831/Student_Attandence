package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.myapplication.R.id.clgId;
import static com.example.myapplication.R.id.studentNameId;
import static com.example.myapplication.R.id.submitButtonId;

public class SecondActivity extends AppCompatActivity {

    private EditText studentIdEditText, studentNameEditText;
    private Button submitBtn;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        studentIdEditText = findViewById(clgId);
        studentNameEditText = findViewById(studentNameId);
        submitBtn = findViewById(submitButtonId);

        databaseReference = FirebaseDatabase.getInstance().getReference("Add Student");


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitStudent();
            }
        });
    }

    public void submitStudent()
    {

       String studentID = studentIdEditText.getText().toString().trim();
       String studentName = studentNameEditText.getText().toString().trim();

       if (studentID.isEmpty()){
        studentIdEditText.setError("Please enter an ID number");
        studentIdEditText.requestFocus();
        return;
    }
        if (studentName.isEmpty()){
            studentNameEditText.setError("Please enter a name");
            studentNameEditText.requestFocus();
            return;
        }

       String key = databaseReference.push().getKey();

       Attendence attendence = new Attendence(studentID,studentName);

       databaseReference.child(key).setValue(attendence);

        Toast.makeText(getApplicationContext(), "Student added successfully", Toast.LENGTH_SHORT).show();


    }
}
