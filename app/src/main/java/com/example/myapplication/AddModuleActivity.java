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

public class AddModuleActivity extends AppCompatActivity {
    EditText sId, sName, sDuration;
    Button btnSave, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        sId = findViewById(R.id.ModuleId);
        sName = findViewById(R.id.ModuleName);
        sDuration = findViewById(R.id.ModuleDuration);
        btnSave = findViewById(R.id.btnSaveModule);
        btnView = findViewById(R.id.btnViewModule);

        btnSave.setOnClickListener(v -> insert());
        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ModuleViewsActivity.class);
            startActivity(intent);
        });
    }

    public void insert() {
        try {
            int Id = Integer.parseInt(sId.getText().toString().trim());
            String Name = sName.getText().toString().trim();
            String Duration = sDuration.getText().toString().trim();

            SQLiteDatabase db = openOrCreateDatabase("taskManagementDB", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS modules(Id INTEGER PRIMARY KEY, Name VARCHAR, Duration TEXT);");

            String sql = "INSERT INTO modules(Id, Name, Duration) VALUES (?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindLong(1, Id);
            statement.bindString(2, Name);
            statement.bindString(3, Duration);

            statement.execute();
            Toast.makeText(this, "New module added", Toast.LENGTH_LONG).show();

            sId.setText("");
            sName.setText("");
            sDuration.setText("");

        } catch (Exception e) {
            Toast.makeText(this, "Failed to add module: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
