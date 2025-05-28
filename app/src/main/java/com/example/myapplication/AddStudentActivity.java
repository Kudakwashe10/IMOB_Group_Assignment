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

public class AddStudentActivity extends AppCompatActivity {
    EditText sId, sName, sSurname, sDob;
    Button btnSave, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        sId = findViewById(R.id.StudentId);
        sName = findViewById(R.id.StudentName);
        sSurname = findViewById(R.id.StudentSurname);
        sDob = findViewById(R.id.StudentDob);
        btnSave = findViewById(R.id.btnSaveStudent);
        btnView = findViewById(R.id.btnViewStudents);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
                Intent intent = new Intent(AddStudentActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void insert() {
        try {
            int Id = Integer.parseInt(sId.getText().toString());
            String Name = sName.getText().toString();
            String Surname = sSurname.getText().toString();
            String Dob = sDob.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("taskManagementDB", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS students(Id INTEGER PRIMARY KEY, Name VARCHAR, Surname VARCHAR, Dob VARCHAR);");

            String sql = "INSERT INTO students(Id, Name, Surname, Dob) VALUES (?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindLong(1, Id);
            statement.bindString(2, Name);
            statement.bindString(3, Surname);
            statement.bindString(4, Dob);

            statement.execute();
            Toast.makeText(AddStudentActivity.this, "New student added", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(AddStudentActivity.this, "Failed to add new student: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
