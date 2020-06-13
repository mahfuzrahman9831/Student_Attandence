package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

import static com.example.myapplication.R.id.signInTextViewId;
import static com.example.myapplication.R.id.signUpButtonId;
import static com.example.myapplication.R.id.signUpEmailEditTextId;
import static com.example.myapplication.R.id.signUpPasswordEditTextId;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText signUpEmailEditText, signUpPasswordEditText;
    private Button signUpButton;
    private TextView signInTextView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpEmailEditText = findViewById(signUpEmailEditTextId);
        signUpPasswordEditText = findViewById(signUpPasswordEditTextId);
        signUpButton = findViewById(signUpButtonId);
        signInTextView = findViewById(signInTextViewId);

        mAuth = FirebaseAuth.getInstance();

        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view.getId()== signInTextViewId){

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }

        if (view.getId()== signUpButtonId){

            userRegirter();
        }

    }

    private void userRegirter() {

        String email = signUpEmailEditText.getText().toString().trim();
        String password = signUpPasswordEditText.getText().toString().trim();

        //checking the validity of email
        if(email.isEmpty()){

            signUpEmailEditText.setError("Enter an email address");
            signUpEmailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpEmailEditText.setError("Enter an valid email address");
            signUpEmailEditText.requestFocus();
            return;
        }

        //checking the validity of password
        if(password.isEmpty()){
            signUpPasswordEditText.setError("Enter a password");
            signUpPasswordEditText.requestFocus();
            return;
        }

        if(password.length()<6){
            signUpPasswordEditText.setError("Minimum length of password should be 6");
            signUpPasswordEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    finish();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    Toasty.success(SignUpActivity.this,"Registered Successfully. Now you can Login here.", Toast.LENGTH_SHORT).show();


                } else {
                    if (task.getException()instanceof FirebaseAuthUserCollisionException){

                        Toasty.warning(SignUpActivity.this,"Already registered using this email address",Toasty.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(SignUpActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
