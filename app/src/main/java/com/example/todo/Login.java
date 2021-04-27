package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText loginEmail, loginPass;
    Button btnlogin;
    TextView registerRqst, forgotPass;

    FirebaseAuth mAuth;

    ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.loginEmail);
        loginPass = findViewById(R.id.loginPass);
        btnlogin = findViewById(R.id.btnLogin);
        registerRqst = findViewById(R.id.registerRqst);
        forgotPass = findViewById(R.id.forgotPass);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        registerRqst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPass.getText().toString().trim();

                if (email.isEmpty()) {
                    loginEmail.setError("Email is required");
                    loginEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    loginEmail.setError("Please provide valid email!");
                    loginEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    loginPass.setError("Email is required");
                    loginPass.requestFocus();
                    return;
                }
                else {
                    loader.setMessage("Login in progress..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Intent i = new Intent(Login.this, HomePage.class);
                                startActivity(i);
                                finish();
                                loader.dismiss();
                            } else {
                                Toast.makeText(Login.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}