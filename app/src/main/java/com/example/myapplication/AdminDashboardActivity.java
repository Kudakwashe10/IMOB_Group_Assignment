package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        findViewById(R.id.btnAddStudent).setOnClickListener(v -> startActivity(new Intent(this, AddStudentActivity.class)));
        findViewById(R.id.btnAddInstructor).setOnClickListener(v -> startActivity(new Intent(this, AddInstructorActivity.class)));
        findViewById(R.id.btnAddModule).setOnClickListener(v -> startActivity(new Intent(this, AddModuleActivity.class)));
    }
}