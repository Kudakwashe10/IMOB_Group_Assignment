package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class InstructorDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_dashboard);

        Toast.makeText(this, "Instructor Dashboard Loaded", Toast.LENGTH_SHORT).show();

        findViewById(R.id.btnCreateTask).setOnClickListener(v ->
                startActivity(new Intent(this, CreateTaskActivity.class)));
    }
}
