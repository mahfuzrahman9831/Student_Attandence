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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

import static com.example.myapplication.R.id.loginButtonId;
import static com.example.myapplication.R.id.loginEmailEditTextId;
import static com.example.myapplication.R.id.loginPasswordEditTextId;
import static com.example.myapplication.R.id.signUpTextViewId;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginEmailEditText, loginPasswordEditText;
    private Button loginButton;
    private TextView signUpTextView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailEditText = findViewById(loginEmailEditTextId);
        loginPasswordEditText = findViewById(loginPasswordEditTextId);
        loginButton = findViewById(loginButtonId);
        signUpTextView = findViewById(signUpTextViewId);
        mAuth = FirebaseAuth.getInstance();


        signUpTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId()== signUpTextViewId){

            Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
            startActivity(intent);

        }

        if (view.getId()== loginButtonId){

            userLogin();

        }
    }

    private void userLogin() {

        String email = loginEmailEditText.getText().toString().trim();
        String password = loginPasswordEditText.getText().toString().trim();

        //checking the validity of email
        if(email.isEmpty()){

            loginEmailEditText.setError("Enter an email address");
            loginEmailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmailEditText.setError("Enter an valid email address");
            loginEmailEditText.requestFocus();
            return;
        }

        //checking the validity of password
        if(password.isEmpty()){
            loginPasswordEditText.setError("Enter a password");
            loginPasswordEditText.requestFocus();
            return;
        }

        if(password.length()<6){
            loginPasswordEditText.setError("Minimum length of password should be 6");
            loginPasswordEditText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Toasty.success(LoginActivity.this,"Login Successfully.", Toasty.LENGTH_SHORT).show();

                    finish();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{

                    Toasty.error(LoginActivity.this,"Login Failed",Toasty.LENGTH_SHORT).show();
                }

            }
        });

    }
}
