package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    Button loginButton ,signInButton;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signInButton = findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            String role = dbHelper.authenticateUser(username, password);

            if (role != null) {
                dbHelper.saveLoginSession(username, role);
                switch (role) {
                    case "admin":
                        startActivity(new Intent(this, AdminDashboardActivity.class));
                        break;
                    case "instructor":
                        startActivity(new Intent(this, InstructorDashboardActivity.class));
                        break;
                    case "student":
                        startActivity(new Intent(this, StudentDashboardActivity.class));
                        break;
                }
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }

        });
    }
}

