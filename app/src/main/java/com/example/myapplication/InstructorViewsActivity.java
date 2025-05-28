package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InstructorViewsActivity extends AppCompatActivity {
    ListView instructorrecords;
    ArrayList<String> instructors = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_views);

        instructorrecords = findViewById(R.id.InstructorListView);

        SQLiteDatabase db = openOrCreateDatabase("taskManagementDB", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM instructor", null); // Must match table name

        int idCol = cursor.getColumnIndex("Id");
        int nameCol = cursor.getColumnIndex("Name");

        instructors.clear();

        if (cursor.moveToFirst()) {
            do {
                String record = cursor.getString(idCol) + "\t" + cursor.getString(nameCol);
                instructors.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, instructors);
        instructorrecords.setAdapter(arrayAdapter);
    }
}
