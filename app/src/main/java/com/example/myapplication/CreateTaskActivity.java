package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateTaskActivity extends AppCompatActivity {
    EditText sId, sName, sDueDate, sModuleID;
    Button btnSave, btnView, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        sId = findViewById(R.id.textView1);
        sName = findViewById(R.id.textView2);
        sDueDate = findViewById(R.id.textView3);
        sModuleID = findViewById(R.id.textView4);
        btnSave = findViewById(R.id.btnSaveTask);
        btnView = findViewById(R.id.btnViewTask);
        btnUpdate = findViewById(R.id.btnUpdateTask);
        btnDelete = findViewById(R.id.btnDeleteTask);

        // Pre-fill fields if editing
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("taskId")) {
            sId.setText(intent.getStringExtra("taskId"));
            sName.setText(intent.getStringExtra("taskName"));
            sDueDate.setText(intent.getStringExtra("taskDueDate"));
            sModuleID.setText(intent.getStringExtra("taskModuleId"));

            sId.setEnabled(false);
        }

        btnSave.setOnClickListener(v -> {
            sId.setEnabled(true);
            insert();
        });

        btnView.setOnClickListener(v -> {
            Intent i = new Intent(CreateTaskActivity.this, ViewTasksActivity.class);
            startActivity(i);
            finish();
        });

        btnUpdate.setOnClickListener(v -> updateTask());

        btnDelete.setOnClickListener(v -> deleteTask());
    }

    public void insert() {
        try {
            int Id = Integer.parseInt(sId.getText().toString());
            String Name = sName.getText().toString();
            String DueDate = sDueDate.getText().toString();
            String ModuleId = sModuleID.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("taskManagementDB", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS tasks(Id INTEGER PRIMARY KEY, Name VARCHAR, DueDate VARCHAR, ModuleId VARCHAR);");

            String sql = "INSERT INTO tasks(Id, Name, DueDate, ModuleId) VALUES (?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindLong(1, Id);
            statement.bindString(2, Name);
            statement.bindString(3, DueDate);
            statement.bindString(4, ModuleId);

            statement.execute();
            Toast.makeText(this, "New task added", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to add new task: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void updateTask() {
        try {
            int Id = Integer.parseInt(sId.getText().toString());
            String Name = sName.getText().toString();
            String DueDate = sDueDate.getText().toString();
            String ModuleId = sModuleID.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("taskManagementDB", Context.MODE_PRIVATE, null);
            String sql = "UPDATE tasks SET Name = ?, DueDate = ?, ModuleId = ? WHERE Id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, Name);
            statement.bindString(2, DueDate);
            statement.bindString(3, ModuleId);
            statement.bindLong(4, Id);

            int rowsAffected = statement.executeUpdateDelete();
            if (rowsAffected > 0) {
                Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void deleteTask() {
        try {
            int Id = Integer.parseInt(sId.getText().toString());

            SQLiteDatabase db = openOrCreateDatabase("taskManagementDB", Context.MODE_PRIVATE, null);
            String sql = "DELETE FROM tasks WHERE Id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindLong(1, Id);

            int rowsAffected = statement.executeUpdateDelete();
            if (rowsAffected > 0) {
                Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Delete failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void clearFields() {
        sId.setText("");
        sName.setText("");
        sDueDate.setText("");
        sModuleID.setText("");
        sId.setEnabled(true);
    }
}
