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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    EditText fullname, registerEmail, registerPass;
    Button btnregister;
    TextView LoginRqst;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        fullname = findViewById(R.id.username);
        registerEmail = findViewById(R.id.enterEmail);
        registerPass = findViewById(R.id.enterPass);
        btnregister = findViewById(R.id.btnRegister);
        LoginRqst = findViewById(R.id.loginRqst);

        LoginRqst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this, Login.class);
                startActivity(i);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullname.getText().toString().trim();
                String email = registerEmail.getText().toString().trim();
                String password = registerPass.getText().toString().trim();

                if(name.isEmpty()){
                    fullname.setError("Name is required");
                    fullname.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    registerEmail.setError("Email is required");
                    registerEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    registerEmail.setError("Please provide valid email!");
                    registerEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    registerPass.setError("Email is required");
                    registerPass.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    registerPass.setError("password length should be atleast 6 characters");
                    registerPass.requestFocus();
                    return;
                }
                else {

                    loader.setMessage("Registration in progress..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                User user = new User(name, email);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Intent i = new Intent(getApplicationContext(), WelcomeSplash.class);
                                            startActivity(i);
                                            finish();
                                        }else {
                                            Toast.makeText(Signup.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(Signup.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}