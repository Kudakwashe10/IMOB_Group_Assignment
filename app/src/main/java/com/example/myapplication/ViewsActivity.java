package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewsActivity extends AppCompatActivity {
    ListView studentrecords;
    ArrayList<String> students = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);

        studentrecords = findViewById(R.id.tasksListView);

        SQLiteDatabase db = openOrCreateDatabase("taskManagementDB", Context.MODE_PRIVATE, null);
        final Cursor cursor = db.rawQuery("SELECT * FROM students", null);

        int idCol = cursor.getColumnIndex("Id");
        int nameCol = cursor.getColumnIndex("Name");
        int surnameCol = cursor.getColumnIndex("Surname");
        int dobCol = cursor.getColumnIndex("Dob");

        students.clear();

        if (cursor.moveToFirst()) {
            do {
                String record = cursor.getString(idCol) + "\t" +
                        cursor.getString(nameCol) + "\t" +
                        cursor.getString(surnameCol) + "\t" +
                        cursor.getString(dobCol);
                students.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();

        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, students);
        studentrecords.setAdapter(arrayAdapter);
    }
}
