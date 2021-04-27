package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Calendar;
import java.util.Random;
import android.text.format.DateFormat;

public class NewTaskActivity extends AppCompatActivity {

    TextView adddate, addTime;
    EditText titledesc, desc;
    int hr, min;
    Button btnSaveTask, btnCancel, btnCalendar;
    DatePickerDialog.OnDateSetListener dateSetListener;

    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String userID;

    Integer num = new Random().nextInt();
    String taskKey = Integer.toString(num);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titledesc = findViewById(R.id.titledesc);
        desc = findViewById(R.id.desc);
        adddate = findViewById(R.id.adddate);
        addTime = findViewById(R.id.addTime);

        btnCalendar = findViewById(R.id.btnCalendar);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();

        adddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(NewTaskActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d("NewTaskActivity", "onDateSet: mm/dd/yyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = month + "/" + dayOfMonth + "/" + year;
                adddate.setText(date);
            }
        };

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hr = hourOfDay;
                        min = minute;

                        Calendar cal = Calendar.getInstance();
                        //setting hour and minute
                        cal.set(0, 0, 0, hr,min);
                        addTime.setText(DateFormat.format("hh:mm aa", cal));
                    }
                }, 12, 0, false);
                timePickerDialog.updateTime(hr, min);
                timePickerDialog.show();
            }
        });

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child("Tasks").child(userID).child("mytasks" + taskKey);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("taskTitle").setValue(titledesc.getText().toString());
                        snapshot.getRef().child("desc").setValue(desc.getText().toString());
                        snapshot.getRef().child("date").setValue(adddate.getText().toString());
                        snapshot.getRef().child("time").setValue(addTime.getText().toString());
                        snapshot.getRef().child("taskKey").setValue(taskKey);

                        Intent intent = new Intent(NewTaskActivity.this, HomePage.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewTaskActivity.this, HomePage.class);
                startActivity(i);
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewTaskActivity.this, Calender.class);
                startActivity(i);
            }
        });
    }
}