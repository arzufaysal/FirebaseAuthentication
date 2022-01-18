package com.arzu.firebaseauthentication;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail,edtPassword;
    Button btnLogin;
    TextView txtRegister;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
        progressBar=findViewById(R.id.progressBarLogin);
        auth= FirebaseAuth.getInstance();
        btnLogin=findViewById(R.id.btnLogin);
        txtRegister=findViewById(R.id.txtRegister);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=edtEmail.getText().toString().trim();
                String password=edtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    edtEmail.setError("Email is required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    edtPassword.setError("Password is required");
                    return;
                }

                if(password.length()<6){
                    edtPassword.setError("Password must be greater than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate the user

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else{
                            Toast.makeText(LoginActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });


            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });


    }
}