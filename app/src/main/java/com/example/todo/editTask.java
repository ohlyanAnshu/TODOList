package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editTask extends AppCompatActivity {
    EditText taskTitle, desc, date;
    TextView time;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        taskTitle = findViewById(R.id.titledesc);
        desc = findViewById(R.id.desc);
        date = findViewById(R.id.selectDate);
        time = findViewById(R.id.selectTime);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();

        //get value from previous page
        taskTitle.setText(getIntent().getStringExtra("taskTitle"));
        desc.setText(getIntent().getStringExtra("desc"));
        date.setText(getIntent().getStringExtra("date"));
        time.setText(getIntent().getStringExtra("time"));

        String taskKey = getIntent().getStringExtra("taskKey");

        reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(userID).child("mytasks" + taskKey);

        //event for button Update
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("taskTitle").setValue(taskTitle.getText().toString());
                        snapshot.getRef().child("desc").setValue(desc.getText().toString());
                        snapshot.getRef().child("date").setValue(date.getText().toString());
                        snapshot.getRef().child("time").setValue(time.getText().toString());
                        snapshot.getRef().child("taskKey").setValue(taskKey );

                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(editTask.this, HomePage.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}