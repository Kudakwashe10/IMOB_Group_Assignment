package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewTasksActivity extends AppCompatActivity {
    ListView taskrecords;
    ArrayList<String> tasks = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        taskrecords = findViewById(R.id.tasksListView);

        SQLiteDatabase db = openOrCreateDatabase("taskManagementDB", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);

        int idCol = cursor.getColumnIndex("Id");
        int nameCol = cursor.getColumnIndex("Name");
        int dueDateCol = cursor.getColumnIndex("DueDate");
        int moduleIdCol = cursor.getColumnIndex("ModuleId");

        tasks.clear();

        if (cursor.moveToFirst()) {
            do {
                String record = cursor.getString(idCol) + "\t" +
                        cursor.getString(nameCol) + "\t" +
                        cursor.getString(dueDateCol) + "\t" +
                        cursor.getString(moduleIdCol);
                tasks.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        taskrecords.setAdapter(arrayAdapter);

        // ✅ Add click listener to launch CreateTaskActivity with data
        taskrecords.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTask = tasks.get(position);

            // Format: "Id\tName\tDueDate\tModuleId"
            String[] parts = selectedTask.split("\t");
            if (parts.length >= 4) {
                Intent intent = new Intent(ViewTasksActivity.this, CreateTaskActivity.class);
                intent.putExtra("taskId", parts[0]);
                intent.putExtra("taskName", parts[1]);
                intent.putExtra("taskDueDate", parts[2]);
                intent.putExtra("taskModuleId", parts[3]);
                startActivity(intent);
            }
        });
    }
}
