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

public class RegisterActivity extends AppCompatActivity {

    EditText edtFullName,edtEmail,edtPassword;
    Button btnRegister;
    TextView txtLogin;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
        btnRegister=findViewById(R.id.btnRegister);
        txtLogin=findViewById(R.id.txtLogin);

        auth= FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        btnRegister.setOnClickListener(new View.OnClickListener() {
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

                //Register the user in  firebase

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else {
                            Toast.makeText(RegisterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });


    }
}