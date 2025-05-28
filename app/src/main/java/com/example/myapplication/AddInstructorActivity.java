package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddInstructorActivity extends AppCompatActivity {
    EditText sId, sName;
    Button btnSave, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);

        sId = findViewById(R.id.InstructorId);
        sName = findViewById(R.id.InstructorName);
        btnSave = findViewById(R.id.btnSaveInstructor);
        btnView = findViewById(R.id.btnViewInstructor);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
                Intent intent = new Intent(AddInstructorActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), InstructorViewsActivity.class);
            startActivity(intent);
        });
    }

    public void insert() {
        try {
            int Id = Integer.parseInt(sId.getText().toString().trim());
            String Name = sName.getText().toString().trim();

            SQLiteDatabase db = openOrCreateDatabase("taskManagementDB", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS instructor(Id INTEGER PRIMARY KEY, Name VARCHAR);");

            String sql = "INSERT INTO instructor(Id, Name) VALUES (?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindLong(1, Id);
            statement.bindString(2, Name);

            statement.execute();
            Toast.makeText(this, "New instructor added", Toast.LENGTH_LONG).show();

            sId.setText("");
            sName.setText("");

        } catch (Exception e) {
            Toast.makeText(this, "Failed to add instructor: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
