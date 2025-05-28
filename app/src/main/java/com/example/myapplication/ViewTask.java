package com.example.myapplication;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


public class ViewTask extends AppCompatActivity {
    ListView tasksListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        tasksListView = findViewById(R.id.tasksListView);
        // Load tasks from database and populate the list view
    }
}