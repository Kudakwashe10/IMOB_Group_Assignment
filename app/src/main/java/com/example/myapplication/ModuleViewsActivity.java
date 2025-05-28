package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ModuleViewsActivity extends AppCompatActivity {
    ListView modulerecords;
    ArrayList<String> modules = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_views);

        modulerecords = findViewById(R.id.ModuleListView);

        SQLiteDatabase db = openOrCreateDatabase("taskManagementDB", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM modules", null);

        int idCol = cursor.getColumnIndex("Id");
        int nameCol = cursor.getColumnIndex("Name");
        int durationCol = cursor.getColumnIndex("Duration");

        modules.clear();

        if (cursor.moveToFirst()) {
            do {
                String record = cursor.getString(idCol) + "\t" +
                        cursor.getString(nameCol) + "\t" +
                        cursor.getString(durationCol);
                modules.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, modules);
        modulerecords.setAdapter(arrayAdapter);
    }
}
